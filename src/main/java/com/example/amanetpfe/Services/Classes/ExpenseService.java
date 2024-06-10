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

    @Override
    public Map<String, BigDecimal> calculateIdealExpenses(Integer idUser) {
        Optional<User> userOptional = userRepository.findById(idUser);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Map<String, BigDecimal> idealExpenses = new HashMap<>();

            BigDecimal income = user.getIncome();

            // Calcul des montants idéaux pour chaque catégorie de dépenses
            BigDecimal necessities = income.multiply(new BigDecimal("0.50"));
            BigDecimal savings = income.multiply(new BigDecimal("0.20"));
            BigDecimal discretionary = income.multiply(new BigDecimal("0.30"));

            // Ajout des montants idéaux à la carte
            idealExpenses.put("Necessities", necessities);
            idealExpenses.put("Savings", savings);
            idealExpenses.put("Discretionary", discretionary);

            return idealExpenses;
        } else {
            throw new RuntimeException("User not found with id: " + idUser);
        }
    }
}
