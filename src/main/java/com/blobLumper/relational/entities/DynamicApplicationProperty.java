package com.blobLumper.relational.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name = "DynamicApplicationProperties")
@Entity
public class DynamicApplicationProperty extends AbstractEntity{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3434575674355L;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "value", nullable = false)
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
