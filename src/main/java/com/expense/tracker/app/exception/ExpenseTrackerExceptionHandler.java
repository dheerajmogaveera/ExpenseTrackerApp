package com.expense.tracker.app.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExpenseTrackerExceptionHandler {

	@ExceptionHandler(NoSuchExpenseException.class)
	public ResponseEntity<Object> noSuchExpenseExceptionHandler(NoSuchExpenseException ex,WebRequest wr) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", ex.getMessage());

		return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<Object> invalidInputExceptionHandler(InvalidInputException ex,WebRequest wr) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", ex.getMessage());

		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}

}
