package com.blobLumper.bolts;

import java.io.File;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class TempBucketGeneratorBolt extends BaseRichBolt{

	private OutputCollector collector;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		
	}
	
	private boolean isBucketAlreadyExists(){
		File baseTempDir = new File(baseTempDirPath);
		File newEmptyDir = new File(baseTempDir, composedUniqueSubPath);
		exists = newEmptyDir.exists();
	}

	@Override
	public void execute(Tuple input) {
		
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}
	
	

}
