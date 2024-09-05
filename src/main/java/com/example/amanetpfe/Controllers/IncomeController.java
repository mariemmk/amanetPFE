package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.Income;
import com.example.amanetpfe.Services.Interfaces.IExpenseService;
import com.example.amanetpfe.Services.Interfaces.IIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    @Autowired
    IIncomeService incomeService;
    @Autowired
    IExpenseService expenseService;
    @PostMapping("/addIncome/{idUser}")
    public Income addIncome(@RequestBody Income income , @PathVariable Integer idUser) {
        return incomeService.addIncome(income , idUser);
    }

    @GetMapping("/listIncomes/{idUser}")
    public List<Income> getAllIncomes(@PathVariable Integer idUser) {
        return incomeService.getAllIncomes(idUser);
    }

    @DeleteMapping("/remove/{idIncome}")
    public void removeIncome(@PathVariable Long idIncome) {
        incomeService.removeIncome(idIncome);
    }

    @PutMapping("/update/{idIncome}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long idIncome, @RequestBody Income updatedIncome) {
        Income income = incomeService.updateIncome(idIncome, updatedIncome);
        if (income != null) {
            return ResponseEntity.ok(income);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/monthly/{idUser}")
    public Map<YearMonth, Map<String, BigDecimal>> getMonthlyData(@PathVariable Integer idUser) {
        Map<YearMonth, BigDecimal> expenses = expenseService.getMonthlyExpenses(idUser);
        Map<YearMonth, BigDecimal> incomes = incomeService.getMonthlyIncome(idUser);

        // Combine both maps into one with income and expense as separate values
        Map<YearMonth, Map<String, BigDecimal>> combinedData = new TreeMap<>();

        Set<YearMonth> months = new HashSet<>(expenses.keySet());
        months.addAll(incomes.keySet());

        for (YearMonth month : months) {
            BigDecimal expense = expenses.getOrDefault(month, BigDecimal.ZERO);
            BigDecimal income = incomes.getOrDefault(month, BigDecimal.ZERO);
            Map<String, BigDecimal> data = new HashMap<>();
            data.put("income", income);
            data.put("expense", expense);
            combinedData.put(month, data);
        }

        return combinedData;
    }



}
