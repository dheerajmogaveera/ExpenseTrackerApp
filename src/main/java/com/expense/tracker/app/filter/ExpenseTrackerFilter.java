package com.expense.tracker.app.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExpenseTrackerFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(ExpenseTrackerFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("request param:{}", request.getAttribute("title"));

		ExpenseTrackerHttpWrapper req = new ExpenseTrackerHttpWrapper((HttpServletRequest) request);
		logger.info("Request URL:{}", req.getRequestURI());
		Map<String,String[]> pathVariables = request.getParameterMap();
		String title = req.getRequestURI().replace("/v1/expenses/", "");
		logger.info("Path Parameter:{}", title);
		logger.info("Request Parameters:{}", pathVariables);
		if (req.getMethod().equalsIgnoreCase("POST") || req.getMethod().equalsIgnoreCase("PUT")) {

			byte[] body = StreamUtils.copyToByteArray(req.getInputStream());

			@SuppressWarnings("unchecked")
			Map<String, Object> jsonRequest = new ObjectMapper().readValue(body, Map.class);
			logger.info("Request Body passed:{}", jsonRequest);
		}
		chain.doFilter(req, response);

	}

}
