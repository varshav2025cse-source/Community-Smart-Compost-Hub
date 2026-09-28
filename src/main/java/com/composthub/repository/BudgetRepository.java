package com.composthub.repository;
import com.composthub.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BudgetRepository extends JpaRepository<Budget, Long> {}