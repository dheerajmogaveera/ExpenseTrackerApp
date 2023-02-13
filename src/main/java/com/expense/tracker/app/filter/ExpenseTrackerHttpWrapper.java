package com.expense.tracker.app.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ExpenseTrackerHttpWrapper extends HttpServletRequestWrapper{
	
	private byte[] body;
	 
    public ExpenseTrackerHttpWrapper(HttpServletRequest request) throws IOException {
        super(request);
 
        this.body = StreamUtils.copyToByteArray(request.getInputStream());
    }
 
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamWrapper(body);
 
    }
    public String getBody() throws StreamReadException, DatabindException, IOException {
    	return new ObjectMapper().readValue(body, Map.class).toString();
    }
}
	 
