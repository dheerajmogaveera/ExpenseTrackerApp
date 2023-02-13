package com.expense.tracker.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expense.tracker.app.contants.ExpenseConstants;
import com.expense.tracker.app.exception.InvalidInputException;
import com.expense.tracker.app.exception.NoSuchExpenseException;
import com.expense.tracker.app.model.Expense;
import com.expense.tracker.app.model.Report;
import com.expense.tracker.app.repository.ExpenseRepository;

@Service
public class ExpenseTrackerService {

	@Autowired
	ExpenseRepository expenseRepository;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public Expense addExpense(Expense expense) {
		expense.setExpenseDate(new Date());
		return expenseRepository.save(expense);
	}

	public Expense deleteExpense(Long id) throws NoSuchExpenseException {

		Optional<Expense> expense = expenseRepository.findById(id);
		if (expense.isEmpty())
			throw new NoSuchExpenseException("No Expense available with id:" + id);

		expenseRepository.deleteById(id);
		return expense.get();
	}

	public Expense updateExpense(Expense expense) throws NoSuchExpenseException {
		Optional<Expense> exp = expenseRepository.findById(expense.getId());
		if (exp.isEmpty())
			throw new NoSuchExpenseException("No Expense with title:" + expense.getTitle() + " found");
		expense.setExpenseDate(exp.get().getExpenseDate());
		return expenseRepository.save(expense);
	}

	public List<Expense> getAllExpenses() {
		List<Expense> expenseList = new ArrayList<>();
		expenseRepository.findAll().forEach(expenseList::add);
		return expenseList;
	}

	public List<Expense> getExpenseByTitle(String title) throws NoSuchExpenseException {
		List<Expense> expense = expenseRepository.findByTitle(title);
		if (expense.isEmpty())
			throw new NoSuchExpenseException("No Expense available with title" + title);

		return expense;
	}

	public Report generateReport(String range, String startDate, String endDate)
			throws InvalidInputException, ParseException {
		List<Expense> expenseList = new ArrayList<>();
		expenseRepository.findAll().forEach(expenseList::add);
		if (isReportInputValid(range, startDate, endDate))
			throw new InvalidInputException();
		if (range.equals(ExpenseConstants.CUSTOM)) {
			Date start = simpleDateFormat.parse(startDate);
			Date end = DateUtils.addDays(simpleDateFormat.parse(endDate),1);
			expenseList = expenseList.stream()
					.filter(o -> o.getExpenseDate().after(start) && o.getExpenseDate().before(end)).collect(Collectors.toList());
		} else {
			int days = ExpenseConstants.WEEK.equalsIgnoreCase(range) ? 7 : 30;
			expenseList = expenseList.stream()
					.filter(o -> o.getExpenseDate().after(DateUtils.addDays(new Date(), -days))).collect(Collectors.toList());
		}

		return generateReport(expenseList);

	}

	private Report generateReport(List<Expense> expenseList) {

		long size = expenseList.size();
		if (size == 0)
			return null;
		HashMap<String, Long> amountByDate = new HashMap<>();
		HashMap<String, Long> noOfExpensePerDay = new HashMap<>();
		long total = 0;
		long count = 0;
		for (Expense e : expenseList) {
			String date = simpleDateFormat.format(e.getExpenseDate());
			if (amountByDate.containsKey(date)) {
				amountByDate.put(date, amountByDate.get(date) + e.getAmount());
			} else
				amountByDate.put(date, e.getAmount());
			count = noOfExpensePerDay.get(date) == null ? 1 : noOfExpensePerDay.get(date) + 1;
			noOfExpensePerDay.put(date, count);
			total = total + e.getAmount();

		}
		return new Report(total, total / amountByDate.size(), total / expenseList.size(), amountByDate, size,
				noOfExpensePerDay);
	}

	private boolean isReportInputValid(String range, String startDate, String endDate) {
		return ExpenseConstants.WEEK.equalsIgnoreCase(range) || ExpenseConstants.MONTH.equalsIgnoreCase(range)
				|| (ExpenseConstants.CUSTOM.equalsIgnoreCase(range) && (startDate != null && endDate != null)) ? false
						: true;
	}
}
