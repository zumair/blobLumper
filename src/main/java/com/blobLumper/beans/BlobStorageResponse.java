package com.blobLumper.beans;

public class BlobStorageResponse {

	
	
	private String host;
	private Long blobId;
	private String browsingPath;
	
	
	public Long getBlobId() {
		return blobId;
	}
	public void setBlobId(final Long blobId) {
		this.blobId = blobId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(final String host) {
		this.host = host;
	}
	public String getBrowsingPath() {
		return browsingPath;
	}
	public void setBrowsingPath(final String browsingPath) {
		this.browsingPath = browsingPath;
	}
	
	
}
