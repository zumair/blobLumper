package com.blobLumper.constants;

import java.util.Map;

import org.springframework.beans.factory.annotation.Configurable;


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

	
	
	public static void init(Map<String, String> propertiesMap){
		DynamicApplicationProperties[] allConstants = values();
		for (DynamicApplicationProperties constant : allConstants) {
			String mapValue = propertiesMap.get(constant.name);
			constant.value  = mapValue;
		}
		
	}
	
	
	
}
