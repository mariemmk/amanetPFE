package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Expense;
import com.example.amanetpfe.Entities.Reclamation;
import com.example.amanetpfe.Entities.User;

import java.util.List;

public interface IExpenseService {

    List<Expense> allExpenses() ;

    Expense addExpense(Expense expense , Integer idUser);

    Expense retrieveExpense(Integer idExpense);

    void removeExpense(Integer idExpense);

    Expense updateExpense(Integer expense);


}
