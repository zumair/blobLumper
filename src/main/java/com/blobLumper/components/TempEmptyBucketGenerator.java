package com.blobLumper.components;

import java.io.File;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import com.blobLumper.beans.TempBucket;
import com.blobLumper.queues.TempEmptyBucketsQueue;
import com.blobLumper.relational.entities.TempBucketDetails;

@Configurable
public class TempEmptyBucketGenerator {
	@Value("#{applicationProperties['emptyBucketGenerator.temp.base.path']}")
	private String baseTempDirPath;
	protected transient Log log = LogFactory.getLog(getClass());

	private TempBucket old_getTempBucket() {
		boolean exists = true;
		File tempDir = null;
		TempBucket tempBucket = new TempBucket();
		TempBucketDetails bucketDetails = new TempBucketDetails();
		while (exists) {
			long mostSignificantBits = Math.abs(UUID.randomUUID()
					.getMostSignificantBits());
			long leastSignificantBits = Math.abs(UUID.randomUUID()
					.getLeastSignificantBits());
			String composedUniqueSubPath = mostSignificantBits + "/"
					+ leastSignificantBits;
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

	private void generateEmptyBucket() {
		try {
			TempBucket tempBucket = old_getTempBucket();
			boolean bucketCreated = tempBucket.generate();
			if (bucketCreated) {
				TempEmptyBucketsQueue.put(tempBucket);
			}
		} catch (Exception e) {
			log.error("An error occurred in generateEmptyBucket", e);
		}
	}

	public void generateBuckets() {
	}
}