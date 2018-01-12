package com.socgen.drgh.processors.Processors.mapper;

import com.jayway.jsonpath.ReadContext;


import org.json.JSONException;
import org.json.JSONObject;

public abstract class MappingEngine{
	
	
	private static final String SPLIT = "@@SPLIT@@";
	private static final String POINT=".";
	private static final String ARRAY="[*]";
	private Mapper mapper;
	private static MappingEngineSimple mappingSimple = new MappingEngineSimple();
	private static MappingEngineSub mappingSub = new MappingEngineSub();
	private static MappingEngineRules mappingEngineRules= new MappingEngineRules();

	
	public static MappingEngine getInstanceOf(MappingElement.MappingElementType eltType)
	{
		if(mappingSub==null)
		{
			mappingSimple = new MappingEngineSimple();
			mappingSub = new MappingEngineSub();
			mappingEngineRules= new MappingEngineRules();
		}
		
		MappingEngine mapping=null;
		if(eltType== MappingElement.MappingElementType.SIMPLE_MAPPING)
		{
			mapping=mappingSimple;
		}

		if(eltType== MappingElement.MappingElementType.SUB_MAPPING)
		{
			mapping=mappingSub; 
		}
		if(eltType== MappingElement.MappingElementType.FUNCTION_MAPPING)
		{
			mapping=mappingEngineRules; 
		}
		return mapping;
	}
	
	public abstract void run(MappingElement mapping, ReadContext ctx ,JSONObject json) throws JSONException, MappingException;
	
	/**
	 * First Implementation must be replaced with the real implementation
	 * @param stringSourceValue
	 * @return
	 */
	protected Object valuate(String stringSourceValue) {
		// TODO Auto-generated method stub
		return stringSourceValue;
	}
	/**
	 * First Implementation must be replaced with the real implementation
	 * @param obj
	 * @return
	 */
	protected String valuatetoString(Object obj)
	{
		return obj.toString();
	}
	
	protected void putElement(JSONObject jobj,  String target, String value) throws JSONException
	{
		if(target.contains("."))
		{
			String sT= target.replaceAll("\\.",SPLIT);
			String s[]= sT.split(SPLIT);
			String firstObject = s[0];
			JSONObject subObject=null;//=(JSONObject)jobj.isNull(firstObject);		
			if(jobj.isNull(firstObject)) subObject= new JSONObject() ; else subObject= jobj.getJSONObject(firstObject) ;
			String subtarget= target.replaceFirst(firstObject+".", "");
			putElement(subObject,  subtarget, value);
			jobj.put(firstObject, subObject);
		}
		else
			if(target.contains("[*]"))
			{
				
			}
			else
			{
				jobj.put(target,value);				
			}
	
	}
}
