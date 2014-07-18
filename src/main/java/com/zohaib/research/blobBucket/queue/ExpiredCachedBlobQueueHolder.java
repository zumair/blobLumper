package com.zohaib.research.blobBucket.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Configurable
public class ExpiredCachedBlobQueueHolder {
	
	//https://pbs.twimg.com/profile_images/471438948825182208/iyMvW6T9_400x400.jpeg
	
	@Value("#{applicationProperties['queue.emptyBuckets.size']}")
	private static  int SIZE = 3;
	
	static class SingletonHolder{
		static BlockingQueue<Object>  queue = new ArrayBlockingQueue<Object>(SIZE);
	}
	
	public static BlockingQueue<Object> getQueue(){
		return SingletonHolder.queue;
	}
	
	private ExpiredCachedBlobQueueHolder(){
		
	}
	
	
}
