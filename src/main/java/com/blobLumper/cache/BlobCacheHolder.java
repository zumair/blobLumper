package com.blobLumper.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlobCacheHolder {
	
	
	static class SingletonHolder{
		static Map<String,Object>  cache = new LinkedHashMap<String, Object>();
	}
	
	private static Map<String,Object>  getCache(){
		return SingletonHolder.cache;
	}
	
	private BlobCacheHolder(){
		
	}
	
	
	
}
