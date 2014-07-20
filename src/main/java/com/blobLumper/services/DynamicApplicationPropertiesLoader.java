package com.blobLumper.services;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blobLumper.enums.DynamicApplicationProperties;
import com.blobLumper.relational.entities.DynamicApplicationProperty;
import com.blobLumper.repositories.jpa.DynamicApplicationPropertiesRepository;

@Service
public class DynamicApplicationPropertiesLoader {

	@Autowired
	private DynamicApplicationPropertiesRepository dynamicApplicationPropertiesRepository;
	
	public void loadApplicationPropertiesFromDatabase(){
	
		List<DynamicApplicationProperty> applicationProperties = dynamicApplicationPropertiesRepository.findAll();
		
		LinkedHashMap<String, String> mapOfProperties = new LinkedHashMap<String, String>();
		for (DynamicApplicationProperty dynamicApplicationProperty : applicationProperties) {
			String propertyName = dynamicApplicationProperty.getName();
			String propertyValue = dynamicApplicationProperty.getValue();
			mapOfProperties.put(propertyName, propertyValue);
			
		}
		
		DynamicApplicationProperties.init(mapOfProperties);
	}
	
}
