package com.socgen.drgh.processors.Processors.mapper;

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.jsonpath.ReadContext;

public class MapperRunner {
	
	
	public JSONObject run(Mapper map, ReadContext ctx) throws MappingException
	{
		  List<MappingElement> list= map.getListMapping();
		    JSONObject jObject = new JSONObject();
		  
		    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				MappingElement smapping = (MappingElement) iterator.next();
				try {
					MappingEngine mapping=MappingEngine.getInstanceOf(smapping.getMappingElementType());
					mapping.run(smapping, ctx, jObject);						
				} catch (JSONException e) {
					throw new MappingException("",e);
				}
			}	
		    return jObject;
	}

}
