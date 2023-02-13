package com.expense.tracker.app.model;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Report {

	private Long totalAmount;
	private Long averageAmountPerDay;
    private Long averageAmountPerExpense;
	private HashMap<String, Long> amountByDate;
    private Long totalExpenses;
    private HashMap<String,Long> numberOfExpensesByDays;
	


	
	
	
	
	
	
}
