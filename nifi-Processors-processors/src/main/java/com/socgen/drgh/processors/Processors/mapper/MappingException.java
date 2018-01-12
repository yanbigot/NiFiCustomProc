package com.socgen.drgh.processors.Processors.mapper;

import org.json.JSONException;

import com.jayway.jsonpath.InvalidPathException;

public class MappingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MappingException(String s ,InvalidPathException e) {
		// TODO Auto-generated constructor stub
		super(s, e);
	}

	public MappingException(String string) {
		// TODO Auto-generated constructor stub
		super(string);
	}

	public MappingException(String s, Exception e) {
		super(s, e);
	}

}
