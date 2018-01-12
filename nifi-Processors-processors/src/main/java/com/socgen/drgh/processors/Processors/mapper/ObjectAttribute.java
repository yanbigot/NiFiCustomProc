package com.socgen.drgh.processors.Processors.mapper;

import java.util.Map;

public class ObjectAttribute extends AbstractAttribute {

	private Map<String,AbstractAttribute> attributes;
	private String objectName ;
	
	public Map<String, AbstractAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, AbstractAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
}
