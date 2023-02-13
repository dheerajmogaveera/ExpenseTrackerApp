package com.expense.tracker.app.repository;

import java.util.List;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

import com.expense.tracker.app.model.Expense;

@Repository
public interface  ExpenseRepository extends DatastoreRepository<Expense,Long> {

	public List<Expense> findByTitle(String title);
}
