package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.Expense;
import com.example.amanetpfe.Entities.Income;
import com.example.amanetpfe.Entities.Reclamation;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Services.Interfaces.IExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private IExpenseService expenseService;

    @PostMapping("/addExpense/{idUser}")
    ResponseEntity<Expense> addExpense(@RequestBody Expense expense , @PathVariable Integer idUser){
        Expense result = expenseService.addExpense(expense , idUser);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/listExpenses/{idUser}")
    public List<Expense> getAllExpenses(@PathVariable Integer idUser) {
        return expenseService.allExpenses(idUser);
    }
    @DeleteMapping("/remove/{idExpense}")
    public void removeExpense(@PathVariable Long idExpense) {
        expenseService.removeExpense(idExpense);
    }

    @GetMapping("/monthly/{idUser}")
    public Map<YearMonth, BigDecimal> getMonthlyExpenses(@PathVariable Integer idUser) {
        return expenseService.getMonthlyExpenses(idUser);
    }
}
