package com.expense.tracker.app.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NoSuchExpenseExceptionTest {
	
	
	@Test
	public void noSuchExpenseExceptionTest() {
		
		NoSuchExpenseException noSuchExpenseException=new NoSuchExpenseException();
		assertEquals(noSuchExpenseException.getMessage(), null);
	}

}
