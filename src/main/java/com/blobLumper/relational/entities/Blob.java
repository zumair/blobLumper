package com.blobLumper.relational.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Table(name = "BlobObject")
@Entity
public class Blob extends AbstractEntity {
	
	@Column(name = "basePathId", nullable = false)
	private Long basePathId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="basePathId", insertable=false, updatable=false)
	private BlobBasePath blobBasePath;
	
	@Column(name = "subFilePath")
	private String subPath;
	
	@Column(name = "fileName")
	private String fileName;
	
	@Column(name = "fileExtension")
	private String extension;
	
	@Column(name="contentType")
	private String contentType;
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	public Long getBasePathId() {
		return basePathId;
	}

	public void setBasePathId(final Long basePathId) {
		this.basePathId = basePathId;
	}

	public String getSubPath() {
		return subPath;
	}

	public void setSubPath(final String subPath) {
		this.subPath = subPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(final String extension) {
		this.extension = extension;
	}

	public BlobBasePath getBlobBasePath() {
		return blobBasePath;
	}

	public void setBlobBasePath(final BlobBasePath blobBasePath) {
		this.blobBasePath = blobBasePath;
	}
	
	public String getFullPath(){
		final String basePath = this.blobBasePath.getBasePath();
		return basePath+this.getSubPath();
		
	}
	
	public String getBrowsingPath(){
		return this.blobBasePath.getHost()+"/files/"+this.getId();
	}
	
	
	
}