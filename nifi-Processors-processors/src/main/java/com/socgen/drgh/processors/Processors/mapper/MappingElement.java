package com.socgen.drgh.processors.Processors.mapper;


public  class MappingElement {
	
	
	public MappingElement() {
	
	}
	public enum MappingElementType
	{
		SIMPLE_MAPPING, SUB_MAPPING, FUNCTION_MAPPING
	};
    private MappingElementType mappingElementType;
	private String source;
	private String target;
	
	
	
	public MappingElementType getMappingElementType() {
		return mappingElementType;
	}
	public void setMappingElementType(MappingElementType mappingElementType) {
		this.mappingElementType = mappingElementType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	

}
