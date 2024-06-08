package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
