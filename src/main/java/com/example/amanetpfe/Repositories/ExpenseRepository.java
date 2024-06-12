package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.Expense;
import com.example.amanetpfe.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense>findByUser (User user);
}
