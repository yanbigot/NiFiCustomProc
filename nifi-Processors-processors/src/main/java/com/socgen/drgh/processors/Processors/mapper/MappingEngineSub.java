package com.socgen.drgh.processors.Processors.mapper;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class MappingEngineSub  extends MappingEngine {

	@Override
	public void run(MappingElement mapping, ReadContext ctx, JSONObject json) throws JSONException, MappingException {
		// TODO Auto-generated method stub
		List<Object> listObject= ctx.read(mapping.getSource());
		if (mapping instanceof SubMappingElement) {
			SubMappingElement subMapping = (SubMappingElement) mapping;		
			JSONArray jArray = new JSONArray();
			for (Object object2 : listObject) {
			   Mapper subMapper  =  subMapping.getSubMapper();
			   JSONObject jObj =(new MapperRunner()).run(subMapper, JsonPath.parse(object2));
			   jArray.put(jObj);
			   }
			 json.put(mapping.getTarget(), jArray);			
						
		} 
		else
		{
			throw new MappingException("An array need submapping element " + mapping.getSource() + " " + mapping.getMappingElementType());
		}
		
	}  
	
	

}
