package com.socgen.drgh.processors.Processors.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.jsonpath.ReadContext;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class MappingEngineRules extends MappingEngine {

	@Override
	public void run(MappingElement mapping, ReadContext ctx, JSONObject json) throws JSONException, MappingException {
		
		if (mapping instanceof MappingElementRules) {
			MappingElementRules sd = (MappingElementRules) mapping;
			Binding sharedData = new Binding()   ;         
			GroovyShell shell = new GroovyShell(sharedData) ;   
			List<String> keysList= new ArrayList<String>(sd.getSources().keySet());
			for (Object jsonKey : keysList) {
				String stringSourceValue = ctx.read("$."+jsonKey.toString());			
				Object sourceValue = valuate(stringSourceValue);		
				sharedData.setProperty(sd.getSources().get(jsonKey), sourceValue);     		
			}		    
			sharedData.setProperty("result", null);
			Date now = new Date();
     		shell.evaluate(sd.getRules());	
     		putElement(json,sd.getTarget(),valuatetoString( sharedData.getProperty("result" )));	
     			  		
		}
		else
		{
			throw new MappingException("Bad instance of mapping " + mapping.getClass());
		}

	}

}
