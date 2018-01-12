package com.socgen.drgh.processors.Processors.mapper;

public class AbstractAttribute {
	public static final String STRING ="String";
	public static final String DOUBLE ="Double";
	public static final String LONG ="Long";
	public static final String INTEGER ="Integer";
	public static final String OBJECT ="Object";
	public static final String ARRAY="ArrayOf";
	public static final String MAP="MapOf";

	private String collectionType;
	private String jsonPath;
	private String localPath;
	private String type;
	private String description;
	private String needed;
	
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	public String getJsonPath() {
		return jsonPath;
	}
	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNeeded() {
		return needed;
	}
	public void setNeeded(String needed) {
		this.needed = needed;
	}
	
}
