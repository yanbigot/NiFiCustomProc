package com.socgen.drgh.processors.Processors.mapper;

import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.ReadContext;

public class MappingEngineSimple extends MappingEngine {
	
	
	@Override
	public void run(MappingElement mapping, ReadContext ctx, JSONObject jsonTarget) throws JSONException, MappingException {				
		try 
		{
			String jsonPathSource= mapping.getSource();
			String stringSourceValue= ctx.read(jsonPathSource);
			Object sourceValue = valuate(stringSourceValue);		
			String strTarget= super.valuatetoString(sourceValue);			
			putElement(jsonTarget,mapping.getTarget(),strTarget);			
				
		}
		catch(InvalidPathException e)
		{
			//System.out.println("PATH  invalid for " + mapping.getSource() +" in " +ctx.toString() );
			throw new  MappingException( "PATH  invalid for " + mapping.getSource() +" in " +ctx.toString() , e );
		  
		}
		

	}
	
	


	

}
