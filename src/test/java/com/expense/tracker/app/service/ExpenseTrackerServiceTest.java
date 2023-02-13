package com.expense.tracker.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.expense.tracker.app.contants.ExpenseConstants;
import com.expense.tracker.app.exception.InvalidInputException;
import com.expense.tracker.app.exception.NoSuchExpenseException;
import com.expense.tracker.app.model.Expense;
import com.expense.tracker.app.model.Report;
import com.expense.tracker.app.repository.ExpenseRepository;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseTrackerServiceTest {

	@InjectMocks
	ExpenseTrackerService expenseTrackerService;
	@Mock
	ExpenseRepository expenseRepository;

	@Test
	public void addExpense() {
		Expense expense = new Expense();
		expense.setTitle("abcd");
		when(expenseRepository.save(expense)).thenReturn(expense);
		assertEquals(expenseTrackerService.addExpense(expense), expense);
		assertEquals(expenseTrackerService.addExpense(expense).getTitle(), "abcd");
	}

	@Test
	public void deleteExpense_IsValid() throws NoSuchExpenseException {
		Expense expense = new Expense();
		expense.setTitle("abcd");
		expense.setId(1l);
		when(expenseRepository.findById(expense.getId())).thenReturn(Optional.ofNullable(expense));
		doNothing().when(expenseRepository).deleteById(expense.getId());
		expenseTrackerService.deleteExpense(expense.getId());
		verify(expenseRepository).deleteById(expense.getId());
		;
		assertEquals(expenseTrackerService.deleteExpense(expense.getId()), expense);
	}

	@Test
	public void deleteExpense_ThrowsException() {
		Expense expense = new Expense();
		expense.setId(1l);
		when(expenseRepository.findById(expense.getId())).thenReturn(Optional.ofNullable(null));
		assertThrows(NoSuchExpenseException.class, () -> expenseTrackerService.deleteExpense(expense.getId()));
	}

	@Test
	public void updateExpense_IsValid() throws NoSuchExpenseException {
		Expense expense = new Expense();
		expense.setTitle("abcd");
		expense.setId(1l);
		when(expenseRepository.findById(expense.getId())).thenReturn(Optional.ofNullable(expense));
		when(expenseRepository.save(expense)).thenReturn(expense);
		assertEquals(expenseTrackerService.updateExpense(expense), expense);
		Optional<Expense> e = Optional.empty();
		when(expenseRepository.findById(expense.getId())).thenReturn(e);
		assertThrows(NoSuchExpenseException.class, () -> {
			expenseTrackerService.updateExpense(expense);
		});

	}

	@Test
	public void updateExpense_ThrowsException() throws NoSuchExpenseException {
		Expense expense = new Expense();
		expense.setId(1l);
		Optional<Expense> e = Optional.empty();
		when(expenseRepository.findById(expense.getId())).thenReturn(e);
		assertThrows(NoSuchExpenseException.class, () -> {
			expenseTrackerService.updateExpense(expense);
		});
	}

	@Test
	public void getExpenseByTitle_IsValid() throws NoSuchExpenseException {
		Expense expense = new Expense();
		expense.setTitle("abcd");
		expense.setId(1l);
		List<Expense> eList = new ArrayList<>();
		eList.add(expense);
		when(expenseRepository.findByTitle(expense.getTitle())).thenReturn(eList);

		assertEquals(expenseTrackerService.getExpenseByTitle("abcd"), eList);
	}

	@Test
	public void getExpenseByTitle_ThrowsException() throws NoSuchExpenseException {
		Expense expense = new Expense();
		expense.setTitle("abcd");
		expense.setId(1l);
		List<Expense> eList = new ArrayList<>();
		when(expenseRepository.findByTitle(expense.getTitle())).thenReturn(eList);

		assertThrows(NoSuchExpenseException.class, () -> expenseTrackerService.getExpenseByTitle("abcd"));
	}

	@Test
	public void getAllExpensesTest() {
		Expense e1 = new Expense();
		Expense e2 = new Expense();
		e1.setTitle("abcd");
		e2.setTitle("defg");
		e1.setAmount(100l);
		e2.setAmount(120l);
		List<Expense> expenseList = new ArrayList<>();
		expenseList.add(e1);
		expenseList.add(e2);
		when(expenseRepository.findAll()).thenReturn(expenseList);
		assertEquals(expenseTrackerService.getAllExpenses().get(0).getTitle(), "abcd");
	}

	@Test
	public void generateReport_Week_IValid() throws InvalidInputException, ParseException {
		Expense e1 = new Expense();
		Expense e2 = new Expense();
		e1.setTitle("abcd");
		e2.setTitle("defg");
		e1.setAmount(100l);
		e2.setAmount(120l);
		e1.setExpenseDate(DateUtils.addDays(new Date(), -7));
		e2.setExpenseDate(DateUtils.addDays(new Date(), -5));
		List<Expense> expenseList = new ArrayList<>();
		expenseList.add(e1);
		expenseList.add(e2);
		when(expenseRepository.findAll()).thenReturn(expenseList);
		Report report = expenseTrackerService.generateReport(ExpenseConstants.WEEK, null, null);
		assertEquals(report.getTotalAmount(), Long.valueOf(120l));

	}

	@Test
	public void generateReportTest_ThrowsException() {
		assertThrows(InvalidInputException.class, () -> {
			expenseTrackerService.generateReport("abcd", null, null);
		});
	}

	@Test
	public void generateReport_Month_IsValid() throws InvalidInputException, ParseException {
		Expense e1 = new Expense();
		Expense e2 = new Expense();
		e1.setTitle("abcd");
		e2.setTitle("defg");
		e1.setAmount(100l);
		e2.setAmount(120l);
		e1.setExpenseDate(DateUtils.addDays(new Date(), -7));
		e2.setExpenseDate(DateUtils.addDays(new Date(), -5));
		List<Expense> expenseList = new ArrayList<>();
		expenseList.add(e1);
		expenseList.add(e2);
		when(expenseRepository.findAll()).thenReturn(expenseList);
		Report report = expenseTrackerService.generateReport(ExpenseConstants.WEEK, null, null);
		assertEquals(report.getTotalAmount(), Long.valueOf(120l));
	}

	@Test
	public void generateReport_Custom_IsValid() throws InvalidInputException, ParseException {
		Expense e1 = new Expense();
		Expense e2 = new Expense();
		e1.setTitle("abcd");
		e2.setTitle("defg");
		e1.setAmount(100l);
		e2.setAmount(120l);
		e1.setExpenseDate(DateUtils.addDays(new Date(), -7));
		e2.setExpenseDate(DateUtils.addDays(new Date(), -5));
		List<Expense> expenseList = new ArrayList<>();
		expenseList.add(e1);
		expenseList.add(e2);
		when(expenseRepository.findAll()).thenReturn(expenseList);
		int day = LocalDate.now().minusDays(7).getDayOfMonth();
		String date = day < 10 ? "0" + day : "" + day;
		int day1 = LocalDate.now().minusDays(4).getDayOfMonth();
		String date1 = day1 < 10 ? "0" + day1 : "" + day1;
		String start = date + "-0" + LocalDate.now().minusDays(7).getMonthValue() + "-"
				+ LocalDate.now().minusDays(7).getYear();
		String end = date1 + "-0" + LocalDate.now().minusDays(4).getMonthValue() + "-"
				+ LocalDate.now().minusDays(4).getYear();
		Report report1 = expenseTrackerService.generateReport(ExpenseConstants.CUSTOM, start, end);
		assertEquals(report1.getTotalAmount(), Long.valueOf(220l));
	}
}
