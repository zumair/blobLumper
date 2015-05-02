package com.blobLumper.web.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blobLumper.relational.entities.Blob;
import com.blobLumper.repositories.jpa.BlobRepository;

@Controller
public class BlobLumperController {

	@Autowired
	private BlobRepository blobRepository;
	
	@RequestMapping(value="/files/{blobId}", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public   FileSystemResource getBlob(@PathVariable("blobId") final Long blobId, final HttpServletResponse response){
		
		final Blob blob = blobRepository.findOne(blobId);
		response.setContentType(blob.getContentType());
		return new FileSystemResource(blob.getFullPath());
		
		
	} 
	
}
