package com.blobLumper.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blobLumper.services.BlobStoreService;
import com.blobLumper.services.DynamicApplicationPropertiesLoader;

@Component
public class SpringInitializingBean implements InitializingBean{

	@Autowired
	private DynamicApplicationPropertiesLoader dynamicApplicationPropertiesLoader;
	
	@Autowired
	private BlobStoreService blobStoreService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		dynamicApplicationPropertiesLoader.loadApplicationPropertiesFromDatabase();
		blobStoreService.checkAllBasePaths();
	}
	
	
	
}
