package com.zohaib.research.blobBucket.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import com.zohaib.research.blobBucket.beans.TempBucket;

@Configurable
public class TempEmptyBucketsQueue {
	
	//https://pbs.twimg.com/profile_images/471438948825182208/iyMvW6T9_400x400.jpeg
	
	@Value("#{applicationProperties['queue.emptyBuckets.size']}")
	private static  int SIZE = 10;
	
	static class SingletonHolder{
		static BlockingQueue<TempBucket>  queue = new ArrayBlockingQueue<TempBucket>(SIZE);
	}
	
	
	public static void put(TempBucket tempBucket) throws InterruptedException{
		SingletonHolder.queue.put(tempBucket);
	}
	
	public static TempBucket take() throws InterruptedException{
		return SingletonHolder.queue.take();
	}
	
	public static int getRemainingCapacity(){
		return SingletonHolder.queue.remainingCapacity();
	}
	
	private TempEmptyBucketsQueue(){
		
	}
	
	
}
