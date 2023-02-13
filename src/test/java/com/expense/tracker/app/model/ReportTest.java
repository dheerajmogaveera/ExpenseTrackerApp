package com.expense.tracker.app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReportTest {
	
	@InjectMocks
	Report report=new Report();
	
	@Test
	public void reportGetterSetterTest() {
		report.setAmountByDate(null);
		report.setTotalAmount(1000l);
		report.setAverageAmountPerDay(100l);
		report.setAverageAmountPerExpense(50l);
		report.setTotalExpenses(10l);
		report.setNumberOfExpensesByDays(null);
		assertEquals(report.getAmountByDate(), null);
		assertEquals(report.getAverageAmountPerDay(), Long.valueOf(100l));
		assertEquals(report.getAverageAmountPerExpense(), Long.valueOf(50l));
		assertEquals(report.getTotalAmount(), Long.valueOf(1000l));
		assertEquals(report.getTotalExpenses(), Long.valueOf(10l));
		assertEquals(report.getNumberOfExpensesByDays(), null);
		report=new Report(null, null, null, null, null, null);
		assertEquals(report.getTotalAmount(), null);
		
		
	}

}
