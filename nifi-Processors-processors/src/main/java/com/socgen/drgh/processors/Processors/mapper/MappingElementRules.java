package com.socgen.drgh.processors.Processors.mapper;

import java.util.HashMap;
import java.util.Map;

import groovy.lang.GroovyCodeSource;

public class MappingElementRules extends MappingElement{
	
	Map<String, String> sources= new HashMap<String, String>();	
	String rules;
	
	public void setRules(String rules) {
		this.rules = rules;
	}


	public Map<String, String> getSources() {
		return sources;
	}


	public void setSources(Map<String, String> sources) {
		this.sources = sources;
	}


	public MappingElementRules() {
		setMappingElementType(MappingElementType.FUNCTION_MAPPING);
	}
	
	
	public void putSource(String sourceKey, String sourceCode)
	{
		sources.put(sourceKey, sourceCode);
	}


	public String getRules() {
		// TODO Auto-generated method stub
		return rules;
	}
	
	
	
	
}
