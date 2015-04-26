package com.blobLumper.services;

import java.io.File;
import java.util.Set;

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
		blobRepository.save(newBlobInstance);
		return null;
	}
	
	
	
	/**
	 * Find base Path which has got lowest folder Count 
	 * @return
	 */
	private BlobBasePath findSuitableBasePathInstance(){
		
		return null;
	}
	
	private void updateFolderCountForBasePath(final BlobBasePath blobBasePath){
		
		blobBasePath.setFolderCount(blobBasePath.getFolderCount() + 1);
		blobBasePathRepository.save(blobBasePath);
	}
	
	private String storeFile(final byte[] file, final Long blobId, final String basePath){
		
		//1) Create folder with id as a name under base path folder
		
		//2) Store bytes/file in that folder you have just created
		
		//3) Return sub file path you have just created for a file e.g: /111/abc.jpg
		
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
		
		return null;
	}
	
	public BlobStorageResponse storeBlob(final byte[] file, final String fullFileName){
		
		final BlobBasePath basePath = findSuitableBasePathInstance();
		final Blob basicBlob = createBasicBlobEntry(basePath);
		final String subFilePath = storeFile(file, basicBlob.getId(), basePath.getBasePath());
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
