package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Expense;
import com.example.amanetpfe.Repositories.ExpenseRepository;
import com.example.amanetpfe.Services.Interfaces.IExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService implements IExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;


    @Override
    public List<Expense> allExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense retrieveExpense(Long idExpense) {
        return null;
    }

    @Override
    public void removeExpense(Long idExpense) {
        expenseRepository.deleteById(idExpense);

    }

    @Override
    public Expense updateExpense(Expense expense) {
        return null;
    }


}
