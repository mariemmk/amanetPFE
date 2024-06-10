package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Expense;
import com.example.amanetpfe.Entities.Reclamation;
import com.example.amanetpfe.Entities.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IExpenseService {

    List<Expense> allExpenses() ;

    Expense addExpense(Expense expense , Integer idUser);

    Expense retrieveExpense(Integer idExpense);

    void removeExpense(Integer idExpense);

    Expense updateExpense(Integer expense);


    Map<String, BigDecimal> calculateIdealExpenses(Integer idUser);
}
