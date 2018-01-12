package com.socgen.drgh.processors.Processors.mapper;

public class SubMappingElement extends MappingElement{
	
	Mapper subMapper;
	public Mapper getSubMapper() {
		return subMapper;
	}
	public void setSubMapper(Mapper subMapper) {
		this.subMapper = subMapper;
	}
	public SubMappingElement() {
		// TODO Auto-generated constructor stub
		setMappingElementType(MappingElementType.SUB_MAPPING);
	}

	
}
