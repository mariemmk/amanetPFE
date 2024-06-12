package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Expense;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.ExpenseRepository;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Services.Interfaces.IExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService implements IExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    IUserRepository userRepository;


    @Override
    public List<Expense> allExpenses(Integer idUser) {
        User user =userRepository.findById(idUser).orElse(null);
        return expenseRepository.findByUser(user);
    }

    @Override
    public Expense addExpense(Expense expense , Integer idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));
        expense.setUser(user);
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
    public Expense updateExpense(Long expense) {
        return null;
    }


}
