package com.blobLumper.services;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blobLumper.beans.BlobStorageResponse;
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
	
	/**
	 * Create a blob record with id and basePath id Only
	 * @return
	 */
	 
	private Blob createBasicBlobEntry(final BlobBasePath blobBasePath){
		final Blob newBlobInstance = new Blob();
		newBlobInstance.setBasePathId(blobBasePath.getId());
		return blobRepository.save(newBlobInstance);
	}
	
	
	
	/**
	 * Find base Path which has got lowest folder Count 
	 * @return
	 */
	private BlobBasePath findSuitableBasePathInstance(){
		
		 final List<BlobBasePath> basePathInstances = blobBasePathRepository.findBasePathWithLeastFolderCount();
		 return basePathInstances.get(0);
	}
	
	private void updateFolderCountForBasePath(final BlobBasePath blobBasePath){
		blobBasePathRepository.updateBasePathFolderCount(blobBasePath.getId());
	}
	
	private String storeFile(final byte[] file, final Long blobId, final String basePathString, final String fileName){
		final File basePath   = new File(basePathString);
		final File fileFolder = new File(basePath, String.valueOf(blobId));
		
		//1) Create folder with id as a name under base path folder
		//2) Store bytes/file in that folder you have just created
		//3) Return sub file path you have just created for a file e.g: /111/abc.jpg
		
		if(!fileFolder.exists()){
			try{
				final boolean fileFolderCreated = fileFolder.mkdir();
				if(fileFolderCreated){
					final File actualFile = new File(fileFolder,fileName);
					FileUtils.writeByteArrayToFile(actualFile, file);
					return "/"+String.valueOf(blobId)+"/"+fileName;
				}
				
			}catch(final Exception e){
				throw new RuntimeException(e);
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param subFilePath will be id of blob Object/fileName e.g: /111/abc.jpg
	 * @param blob
	 * @param fileName
	 * @param fileExtension
	 * @return
	 */
	private Blob updateBlobEntry(final String subFilePath, final Blob blob, final String fileName, final String fileExtension ){
		
		blob.setFileName(fileName);
		blob.setSubPath(subFilePath);
		blob.setExtension(fileExtension);
		return blobRepository.save(blob);
	}
	
	private String getFileExtensionOfFile(final String fullFileName){
		
		return FilenameUtils.getExtension(fullFileName);
	}
	
	public BlobStorageResponse storeBlob(final byte[] file, final String fullFileName){
		
		final BlobBasePath basePath = findSuitableBasePathInstance();
		final Blob basicBlob = createBasicBlobEntry(basePath);
		final String subFilePath = storeFile(file, basicBlob.getId(), basePath.getBasePath(),fullFileName);
		final String fileExtension = getFileExtensionOfFile(fullFileName);
		updateBlobEntry( subFilePath, basicBlob, fullFileName, fileExtension);
		updateFolderCountForBasePath(basePath);
		final BlobStorageResponse blobStorageResponse = new BlobStorageResponse();
		blobStorageResponse.setHost(basePath.getHost());
		blobStorageResponse.setBlobId(basicBlob.getId());
		return blobStorageResponse;
	}
	
	
	public void checkAllBasePaths(){
		
		final String msgPerBlobBasePath = "%s path does not exists on your system";
		final StringBuilder msgBuilder  = new StringBuilder();
		boolean isAnyBasePathIncorrect  = false;
		final Set<BlobBasePath> activeBasePaths = blobBasePathRepository.findAllActiveBasePaths();
		for (final BlobBasePath blobBasePath : activeBasePaths) {
			final String basePath = blobBasePath.getBasePath();
			final File basePathFileInstance = new File(basePath);
			if(! basePathFileInstance.exists()){
				isAnyBasePathIncorrect = true;
				msgBuilder.append("\n");
				msgBuilder.append( String.format(msgPerBlobBasePath, basePath) );
				
			}
		}
		if(isAnyBasePathIncorrect){
			throw new RuntimeException(msgBuilder.toString());
		}
	}
	
	
}
