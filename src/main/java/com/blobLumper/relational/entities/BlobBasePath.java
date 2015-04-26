package com.blobLumper.relational.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name = "BlobBasePath")
@Entity
public class BlobBasePath extends AbstractEntity {
	
	@Column(name = "host", nullable = false)
	private String host;
	
	@Column(name = "basePath", nullable = false)
	private String basePath;
	
	@Column(name = "folderCount")
	private Long folderCount;
	
	
	
	@Column(name = "active")
	private Boolean active;
	
	

	public Boolean getActive() {
		return active;
	}

	public void setActive(final Boolean active) {
		this.active = active;
	}

	public String getHost() {
		return host;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(final String basePath) {
		this.basePath = basePath;
	}

	public Long getFolderCount() {
		return folderCount;
	}

	public void setFolderCount(final Long folderCount) {
		this.folderCount = folderCount;
	}



	
	
	
	
}