package com.blobLumper.spouts;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import com.blobLumper.beans.TempBucket;
import com.blobLumper.queues.TempEmptyBucketsQueue;
import com.blobLumper.relational.entities.TempBucketDetails;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

@Configurable
public class TempEmptyBucketSpout extends BaseRichSpout{
	
	
	private static final long serialVersionUID = -6784840113643367767L;
	
	@Value("#{applicationProperties['emptyBucketGenerator.temp.base.path']}")
	private String baseTempDirPath;
	
	private SpoutOutputCollector collector;
	private TopologyContext      topologyContext;

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		
		this.collector = collector;
		this.topologyContext = context;
		
	}
	
	private TempBucket getTempBucket(){
		
		boolean exists 		  = true;
		File tempDir		  = null;
		
		TempBucket tempBucket = new TempBucket();
		TempBucketDetails bucketDetails = new TempBucketDetails();
		while(exists){
			long mostSignificantBits  = Math.abs( UUID.randomUUID().getMostSignificantBits()  );
			long leastSignificantBits = Math.abs( UUID.randomUUID().getLeastSignificantBits() );
			String composedUniqueSubPath = mostSignificantBits+"/"+leastSignificantBits;
			
			File baseTempDir = new File(baseTempDirPath);
			File newEmptyDir = new File(baseTempDir, composedUniqueSubPath);
			exists = newEmptyDir.exists();
			
			bucketDetails.setCreatedDate(new Date());
			bucketDetails.setLeastSignificantBits(leastSignificantBits);
			bucketDetails.setMostSignificantBits(mostSignificantBits);
			
			tempBucket.setDetails(bucketDetails);
			tempBucket.setDirectory(newEmptyDir);
			tempBucket.setPath(composedUniqueSubPath);
			
		}
		return tempBucket;
	}
	
	

	@Override
	public void nextTuple() {
		//If there is a place to place item on queue
		if(TempEmptyBucketsQueue.getRemainingCapacity() > 0){
			TempBucket tempBucket = getTempBucket();
			Values tupleValues = new Values(tempBucket);
			collector.emit(tupleValues);
		}else{
			Utils.sleep(1000);
		}
		
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tempBucket"));
		
	}
	
	
	
	
}
