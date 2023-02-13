package com.expense.tracker.app.controller;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.tracker.app.exception.InvalidInputException;
import com.expense.tracker.app.exception.NoSuchExpenseException;
import com.expense.tracker.app.model.Expense;
import com.expense.tracker.app.model.Report;
import com.expense.tracker.app.service.ExpenseTrackerService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author dheeraj
 *
 */
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/v1/expenses")
public class ExpensesTrackerController {

	@Autowired
	private ExpenseTrackerService expenseTrackerService;

	@GetMapping
	public ResponseEntity<List<Expense>> getAllExpenses() {
		HttpHeaders responseHeaders=new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "http://localhost:3000");
		return  ResponseEntity.ok().headers(responseHeaders).body(expenseTrackerService.getAllExpenses()) ;
	}

	@GetMapping("/{title}")
	public ResponseEntity<List<Expense>> getExpenseByTitle(@PathVariable("title") String title) throws NoSuchExpenseException {
		return new ResponseEntity<List<Expense>>(expenseTrackerService.getExpenseByTitle(title), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Expense> addExpense(@RequestBody Expense expense)
			throws JsonProcessingException, InvalidInputException {

		if (StringUtils.isBlank(expense.getTitle()) || StringUtils.isBlank(expense.getAmount().toString())
				|| expense.getCategories().isEmpty() || expense.getCategories() == null)
			throw new InvalidInputException();
		return new ResponseEntity<Expense>(expenseTrackerService.addExpense(expense), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense)
			throws JsonProcessingException, NoSuchExpenseException {
		return new ResponseEntity<Expense>(expenseTrackerService.updateExpense(expense), HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Expense> deleteExpense(@PathVariable("id") Long id) throws NoSuchExpenseException {
		return new ResponseEntity<Expense>(expenseTrackerService.deleteExpense(id), HttpStatus.OK);
	}

	@GetMapping("/report")
	public ResponseEntity<Report> generateReport(@RequestParam String range,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate)
			throws InvalidInputException, ParseException {
		return new ResponseEntity<Report>(expenseTrackerService.generateReport(range, startDate, endDate),
				HttpStatus.OK);
	}

}
