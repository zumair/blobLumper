package com.blobLumper.services;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.blobLumper.constants.DynamicApplicationProperties;
import com.blobLumper.relational.entities.Blob;
import com.blobLumper.relational.entities.BlobBasePath;
import com.blobLumper.repositories.jpa.BlobBasePathRepository;
import com.blobLumper.repositories.jpa.BlobRepository;

@Service
public class BlobStoreService {

	@Autowired
	private BlobBasePathRepository blobBasePathRepository;

	@Autowired
	private BlobRepository blobRepository;

	
	public boolean isValidFileSize(final long fileSize){
		final String maxFileSizeString 	= DynamicApplicationProperties.MAX_FILE_SIZE.getValue();
		final long   maxFileSize			= Long.parseLong(maxFileSizeString);
		if(maxFileSize >= fileSize){
			return true;
		}
		return false;
	}
	
	/**
	 * Create a blob record with id and basePath id Only
	 * 
	 * @return
	 */

	private Blob createBasicBlobEntry(final BlobBasePath blobBasePath,
			final String contentType) {
		final Blob newBlobInstance = new Blob();
		newBlobInstance.setBasePathId(blobBasePath.getId());
		newBlobInstance.setContentType(contentType);
		return blobRepository.save(newBlobInstance);
	}

	/**
	 * Find base Path which has got lowest folder Count
	 * 
	 * @return
	 */
	private BlobBasePath findSuitableBasePathInstance() {

		final List<BlobBasePath> basePathInstances = blobBasePathRepository
				.findBasePathWithLeastFolderCount();
		return basePathInstances.get(0);
	}

	private synchronized void updateFolderCountForBasePath(
			final BlobBasePath blobBasePath) {
		final BlobBasePath refreshedBlobBasePathInstance = blobBasePathRepository
				.findOne(blobBasePath.getId());
		refreshedBlobBasePathInstance
				.setFolderCount(refreshedBlobBasePathInstance.getFolderCount() == null ? 0L
						: refreshedBlobBasePathInstance.getFolderCount() + 1L);
		blobBasePathRepository.save(refreshedBlobBasePathInstance);
	}

	private String getValidatedSubPath(final String subPath,
			final String basePath) {
		final File file = new File(basePath + subPath);
		if (file.exists()) {
			return subPath;
		}
		throw new RuntimeException("File does not exist against path="
				+ basePath + subPath);
	}

	private String storeFile(final byte[] file, final Long blobId,
			final String basePathString, final String fileName) {
		final File basePath = new File(basePathString);
		final File fileFolder = new File(basePath, String.valueOf(blobId));

		// 1) Create folder with id as a name under base path folder
		// 2) Store bytes/file in that folder you have just created
		// 3) Return sub file path you have just created for a file e.g:
		// /111/abc.jpg
		

		try {
			final boolean fileFolderCreated = fileFolder.exists()? true: fileFolder.mkdirs();
			if (fileFolderCreated) {
				final File actualFile = new File(fileFolder, fileName);
				if(!actualFile.exists()){
					FileUtils.writeByteArrayToFile(actualFile, file);
				}
				final String subPathString = "/" + String.valueOf(blobId) + "/"
						+ fileName;
				return getValidatedSubPath(subPathString, basePathString);
			}

		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		throw new RuntimeException("Could not store a file on "+fileFolder.getAbsolutePath());
	}

	/**
	 * 
	 * @param subFilePath
	 *            will be id of blob Object/fileName e.g: /111/abc.jpg
	 * @param blob
	 * @param fileName
	 * @param fileExtension
	 * @return
	 */
	private Blob updateBlobEntry(final String subFilePath, final Blob blob,
			final String fileName, final String fileExtension) {

		blob.setFileName(fileName);
		blob.setSubPath(subFilePath);
		blob.setExtension(fileExtension);
		return blobRepository.save(blob);
	}

	private String getFileExtension(final String fullFileName) {

		return FilenameUtils.getExtension(fullFileName);
	}

	public boolean deleteFileAgainstBlobEntryIfExists(final Blob blob){
		final String blobPath = blob.getFullPath();
		final File   file     = new File(blob.getFullPath());
		if(file.exists() && file.isFile()){
			return file.delete();
		}
		return false;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Throwable.class)
	public Long replaceBlob(final byte[] file,
			final String fullFileName, final String contentType, final Long blobId) {

		final Blob blob = blobRepository.findOne(blobId);
		deleteFileAgainstBlobEntryIfExists(blob);
		final String subFilePath =storeFile(file, blobId, blob.getBlobBasePath().getBasePath() , fullFileName);
		final String fileExtension = getFileExtension(fullFileName);
		blob.setContentType(contentType);
		blob.setExtension(fileExtension);
		blob.setSubPath(subFilePath);
		blob.setFileName(fullFileName);
		blobRepository.save(blob);
		
		return blobId;
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Throwable.class)
	public Long storeBlob(final byte[] file,
			final String fullFileName, final String contentType) {

		final BlobBasePath basePath = findSuitableBasePathInstance();
		final Blob basicBlob = createBasicBlobEntry(basePath, contentType);
		final String subFilePath = storeFile(file, basicBlob.getId(),
				basePath.getBasePath(), fullFileName);
		final String fileExtension = getFileExtension(fullFileName);
		updateBlobEntry(subFilePath, basicBlob, fullFileName, fileExtension);
		updateFolderCountForBasePath(basePath);
		
		return basicBlob.getId();
	}

	public void checkAllBasePaths() {

		final String msgPerBlobBasePath = "%s path does not exists on your system";
		final StringBuilder msgBuilder = new StringBuilder();
		boolean isAnyBasePathIncorrect = false;
		final Set<BlobBasePath> activeBasePaths = blobBasePathRepository
				.findAllActiveBasePaths();
		for (final BlobBasePath blobBasePath : activeBasePaths) {
			final String basePath = blobBasePath.getBasePath();
			final File basePathFileInstance = new File(basePath);
			if (!basePathFileInstance.exists()) {
				isAnyBasePathIncorrect = true;
				msgBuilder.append("\n");
				msgBuilder.append(String.format(msgPerBlobBasePath, basePath));

			}
		}
		if (isAnyBasePathIncorrect) {
			throw new RuntimeException(msgBuilder.toString());
		}
	}

}
