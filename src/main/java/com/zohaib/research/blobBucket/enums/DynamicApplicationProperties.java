package com.zohaib.research.blobBucket.enums;

public enum DynamicApplicationProperties {

	BASE_TEMP_DIR("emptyBucket.temp.base.path");
	
	private String name;
	private String value;
	
	private DynamicApplicationProperties(String name){
		this.name = name;
	}	

	public String getValue() {
		return value;
	}

	public void init(String value) {
		this.value = value;
	}
	
}
