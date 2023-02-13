package com.expense.tracker.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.expense.tracker.app.contants.ExpenseConstants;
import com.expense.tracker.app.exception.InvalidInputException;
import com.expense.tracker.app.exception.NoSuchExpenseException;
import com.expense.tracker.app.model.Expense;
import com.expense.tracker.app.model.Report;
import com.expense.tracker.app.service.ExpenseTrackerService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseTrackerControllerTest {

	@InjectMocks
	ExpensesTrackerController expensesTrackerController;

	@Mock
	ExpenseTrackerService expenseTrackerService;

	@Test
	public void addExpense_IsValid() throws JsonProcessingException, InvalidInputException {
		Expense expense = new Expense();
		expense.setTitle("test");
		expense.setAmount(100l);
		List<String> categoryList = new ArrayList<>();
		categoryList.add("Fuel");
		expense.setCategories(categoryList);
		when(expenseTrackerService.addExpense(expense)).thenReturn(expense);
		assertEquals(expensesTrackerController.addExpense(expense).getStatusCode(), HttpStatus.CREATED);
		
	}

	@Test
	public void addExpense_ThrowsException() throws JsonProcessingException, InvalidInputException {
		Expense expense = new Expense();
		expense.setTitle(null);
		assertThrows(InvalidInputException.class, () -> {
			expensesTrackerController.addExpense(expense);
		});
	}
	@Test
	public void updateExpenseTest() throws NoSuchExpenseException, JsonProcessingException {
		Expense expense = new Expense();
		expense.setTitle("test");
		expense.setAmount(100l);
		List<String> categoryList = new ArrayList<>();
		categoryList.add("Fuel");
		expense.setCategories(categoryList);
		when(expenseTrackerService.updateExpense(expense)).thenReturn(expense);
		assertEquals(expensesTrackerController.updateExpense(expense).getStatusCode(), HttpStatus.NO_CONTENT);
	}

	@Test
	public void deleteExpenseTest() throws NoSuchExpenseException {
		Expense expense = new Expense();
		expense.setTitle("test");
		expense.setAmount(100l);
		expense.setId(1l);
		List<String> categoryList = new ArrayList<>();
		categoryList.add("Fuel");
		expense.setCategories(categoryList);
		when(expenseTrackerService.deleteExpense(expense.getId())).thenReturn(expense);
		assertEquals(expensesTrackerController.deleteExpense(expense.getId()).getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getAllExpenses() {
		Expense e1 = new Expense();
		Expense e2 = new Expense();
		e1.setTitle("abcd");
		e2.setTitle("defg");
		e1.setAmount(100l);
		e2.setAmount(120l);
		List<Expense> expenseList = new ArrayList<>();
		expenseList.add(e1);
		expenseList.add(e2);
		when(expenseTrackerService.getAllExpenses()).thenReturn(expenseList);
		assertEquals(expensesTrackerController.getAllExpenses().getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getExpenseByTitleTest() throws NoSuchExpenseException {
		Expense expense = new Expense();
		expense.setTitle("test");
		expense.setAmount(100l);
		List<String> categoryList = new ArrayList<>();
		categoryList.add("Fuel");
		expense.setCategories(categoryList);
		List<Expense> eList=new ArrayList<>();
		eList.add(expense);
		when(expenseTrackerService.getExpenseByTitle("test")).thenReturn(eList);
		assertEquals(expensesTrackerController.getExpenseByTitle("test").getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void generateReportTest() throws InvalidInputException, ParseException {
		Report report = new Report();
		report.setTotalAmount(100l);
		report.setTotalExpenses(2l);
		when(expenseTrackerService.generateReport(ExpenseConstants.WEEK, null, null)).thenReturn(report);
		assertEquals(expensesTrackerController.generateReport(ExpenseConstants.WEEK, null, null).getStatusCode(),
				HttpStatus.OK);

	}

}
