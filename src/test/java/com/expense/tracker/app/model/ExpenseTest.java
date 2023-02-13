package com.expense.tracker.app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseTest {
	
	@InjectMocks
	Expense expense;

	@Test
	public void expenseGetterSetterTest() {
		expense.setAmount(500l);
		List<String> categoryList=new ArrayList<>();
		categoryList.add("Fuel");
		expense.setCategories(categoryList);
		Date date=new Date();
		expense.setExpenseDate(date);
		expense.setTitle("test");
		expense.setNote("Sample Note");
		assertEquals(expense.getAmount(), Long.valueOf(500l));
		assertEquals(expense.getTitle(), "test");
		assertEquals(expense.getNote(), "Sample Note");
		assertEquals(expense.getExpenseDate(), date);
		assertEquals(expense.getCategories().get(0), "Fuel");
	}
	
}
