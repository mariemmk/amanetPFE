package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Expense;
import com.example.amanetpfe.Entities.Reclamation;
import com.example.amanetpfe.Entities.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IExpenseService {

    List<Expense> allExpenses(Integer idUser) ;

    Expense addExpense(Expense expense , Integer idUser);

    Expense retrieveExpense(Long idExpense);

    void removeExpense(Long idExpense);

    Expense updateExpense(Long expense);



}
