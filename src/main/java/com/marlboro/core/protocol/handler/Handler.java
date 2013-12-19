package com.marlboro.core.protocol.handler;

import org.junit.After;
import org.junit.Before;

import com.marlboro.core.protocol.request.Request;
import com.marlboro.core.protocol.response.Response;
import com.marlboro.exception.MarlboroException;


public abstract class Handler <I extends Request, O extends Response> {
	private I request;
	private O response;
	
	public I getRequest() {
		return request;
	}

	public void setRequest(I request) {
		this.request = request;
	}

	public O getResponse() {
		return response;
	}

	public void setResponse(O response) {
		this.response = response;
	}

	public abstract void handle() throws MarlboroException;
	
//	@Before
//	public abstract void before() throws MarlboroException;
//	
//	@After
//	public abstract void after()throws MarlboroException;
}
