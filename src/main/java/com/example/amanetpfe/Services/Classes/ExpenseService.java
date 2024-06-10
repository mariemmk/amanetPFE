package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Expense;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.ExpenseRepository;
import com.example.amanetpfe.Repositories.IUserRepository;
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
    @Autowired
    IUserRepository userRepository;


    @Override
    public List<Expense> allExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense addExpense(Expense expense , Integer idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    @Override
    public Expense retrieveExpense(Integer idExpense) {
        return null;
    }

    @Override
    public void removeExpense(Integer idExpense) {
        expenseRepository.deleteById(idExpense);

    }

    @Override
    public Expense updateExpense(Integer expense) {
        return null;
    }


}
