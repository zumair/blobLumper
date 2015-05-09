package com.blobLumper.constants;

import java.util.Map;


public enum DynamicApplicationProperties {

	BASE_TEMP_DIR("emptyBucket.temp.base.path"),
	MAX_FILE_SIZE("maxFileSizeInBytesToStore");
	
	private String name;
	private String value;
	
	private DynamicApplicationProperties(final String name){
		this.name = name;
	}	

	public String getValue() {
		return value;
	}

	
	
	public static void init(final Map<String, String> propertiesMap){
		final DynamicApplicationProperties[] allConstants = values();
		for (final DynamicApplicationProperties constant : allConstants) {
			final String mapValue = propertiesMap.get(constant.name);
			constant.value  = mapValue;
		}
		
	}
	
	
	
}
