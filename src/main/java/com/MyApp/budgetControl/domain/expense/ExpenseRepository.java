package com.MyApp.budgetControl.domain.expense;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ExpenseRepository extends CrudRepository<ExpenseEntity, UUID> {}
