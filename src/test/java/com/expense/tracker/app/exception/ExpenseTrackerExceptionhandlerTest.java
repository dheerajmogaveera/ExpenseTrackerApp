package com.expense.tracker.app.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseTrackerExceptionhandlerTest {

	@InjectMocks
	ExpenseTrackerExceptionHandler exceptionHandler;
	
	
	@Test
	public void exceptionHandlerTest() {
		Optional<Map<String, Object>> map=Optional.ofNullable((Map<String, Object>) exceptionHandler.noSuchExpenseExceptionHandler(new NoSuchExpenseException("No Expense available with the specified title"),null).getBody());
		assertEquals(map.get().get("message"), "No Expense available with the specified title");
		 map=Optional.ofNullable((Map<String, Object>) exceptionHandler.invalidInputExceptionHandler(new InvalidInputException("Invalid input passed in the query params"),null).getBody());
		assertEquals(map.get().get("message"), "Invalid input passed in the query params");
	}
}
