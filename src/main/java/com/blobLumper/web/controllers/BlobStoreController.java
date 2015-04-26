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
import com.blobLumper.services.BlobStoreService;

@Controller
@RequestMapping("/StoreHouse")
public class BlobStoreController {
	
	@Autowired
	private BlobStoreService blobStoreService;
	
	@RequestMapping(value="/storeBlob", method=RequestMethod.POST)
	public @ResponseBody BlobStorageResponse storeBlob(
			@RequestParam("fileForUpload") final MultipartFile multipartFile,
			final HttpServletRequest request) throws IOException
	{
	
		
		final String fullFileName = multipartFile.getOriginalFilename();
		final byte[] fileBytes = multipartFile.getBytes();
		return blobStoreService.storeBlob(fileBytes, fullFileName);
		
	}
	
	
	
	
}
