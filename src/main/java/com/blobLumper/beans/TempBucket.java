package com.blobLumper.beans;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.blobLumper.relational.entities.TempBucketDetails;
import com.blobLumper.repositories.jpa.TempBucketDetailsRepository;

@Configurable
public class TempBucket {

	private TempBucketDetails details;
	private File directory;
	private String path;
	
	@Autowired
	private transient TempBucketDetailsRepository tempBucketDetailsRepository;

	public boolean generate() {
		this.tempBucketDetailsRepository.save(details);
		this.directory.mkdirs();
		return true;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public TempBucketDetails getDetails() {
		return details;
	}

	public void setDetails(TempBucketDetails details) {
		this.details = details;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

}
