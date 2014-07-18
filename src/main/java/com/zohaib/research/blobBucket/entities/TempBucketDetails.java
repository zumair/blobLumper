package com.zohaib.research.blobBucket.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.zohaib.research.blobBucket.relational.entities.AbstractEntity;

@Table(name = "TempBucketDetails")
@Entity
public class TempBucketDetails extends AbstractEntity {
	private static final long serialVersionUID = 5112296850325611739L;
	@Column(name = "mostSignificantBits", nullable = false)
	Long mostSignificantBits;
	@Column(name = "leastSignificantBits", nullable = false)
	Long leastSignificantBits;
	@Column(name = "createdDate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	Date createdDate;

	public Long getMostSignificantBits() {
		return mostSignificantBits;
	}

	public void setMostSignificantBits(Long mostSignificantBits) {
		this.mostSignificantBits = mostSignificantBits;
	}

	public Long getLeastSignificantBits() {
		return leastSignificantBits;
	}

	public void setLeastSignificantBits(Long leastSignificantBits) {
		this.leastSignificantBits = leastSignificantBits;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}