package com.expense.tracker.app.model;

import java.util.Date;
import java.util.List;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

	@Id
	private Long id;
	private String title;

	private Long amount;

	private List<String> categories;
	
	private String note;
	

	private Date expenseDate;

	

}
