package com.zohaib.research.blobBucket.web.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller("/BlobStorageService")
public class BlobStorageService {

	
	
	public ModelAndView storeBlob(
			@RequestParam("fileForUpload") MultipartFile multipartFile,
			HttpServletRequest request)
	{
			
		return null;
		
	}
	
	
	public ModelAndView getBlob(
			@RequestParam("blobId") String blobId,
			HttpServletRequest request)
	{
			
		return null;
		
	}
	
}
