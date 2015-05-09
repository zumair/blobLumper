package com.blobLumper.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blobLumper.beans.BlobStorageResponse;
import com.blobLumper.relational.entities.Blob;
import com.blobLumper.repositories.jpa.BlobRepository;
import com.blobLumper.services.BlobStoreService;

@Controller
@RequestMapping("/StoreHouse")
public class BlobStoreController {
	
	@Autowired
	private BlobStoreService blobStoreService;
	
	@Autowired
	private BlobRepository blobRepository;
	
	
	
	@RequestMapping(value="/storeBlob", method=RequestMethod.POST)
	public @ResponseBody BlobStorageResponse storeBlob(
			@RequestParam("fileForUpload") final MultipartFile multipartFile,
			final HttpServletRequest request) throws IOException
	{
	
		
		final String fullFileName = multipartFile.getOriginalFilename();
		final byte[] fileBytes = multipartFile.getBytes();
		final String contentType = multipartFile.getContentType();
		final Long blobId = blobStoreService.storeBlob(fileBytes, fullFileName,contentType);
		return createSuccessfullStorageResponse(blobId);
	}

	private BlobStorageResponse createSuccessfullStorageResponse(
			final Long blobId) {
		final Blob blob = blobRepository.findOne(blobId);
		final BlobStorageResponse blobStorageResponse = new BlobStorageResponse();
		blobStorageResponse.setHost(blob.getBlobBasePath().getHost());
		blobStorageResponse.setBlobId(blob.getId());
		blobStorageResponse.setBrowsingPath(blob.getBrowsingPath());
		return blobStorageResponse;
	}
	
	@RequestMapping(value="/overwriteBlob", method=RequestMethod.POST)
	public @ResponseBody BlobStorageResponse overwriteBlob(
			@RequestParam("fileForUpload") 	final MultipartFile multipartFile,
			@RequestParam("blobId")			final Long blobId,
			final HttpServletRequest request) throws IOException
	{
		final String fullFileName = multipartFile.getOriginalFilename();
		final byte[] fileBytes = multipartFile.getBytes();
		final String contentType = multipartFile.getContentType();
		blobStoreService.replaceBlob(fileBytes, fullFileName, contentType, blobId);
		return createSuccessfullStorageResponse(blobId);
		
	}
	
	
	
	
}
