package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Expense;
import com.example.amanetpfe.Entities.Reclamation;

import java.util.List;

public interface IExpenseService {

    List<Expense> allExpenses() ;

    Expense addExpense(Expense expense );

    Expense retrieveExpense(Long idExpense);

    void removeExpense(Long idExpense);

    Expense updateExpense(Expense expense);


}
