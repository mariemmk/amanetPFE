package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.Income;
import com.example.amanetpfe.Services.Interfaces.IIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    @Autowired
    IIncomeService incomeService;
    @PostMapping("/addIncome/{idUser}")
    public Income addIncome(@RequestBody Income income , @PathVariable Integer idUser) {
        return incomeService.addIncome(income , idUser);
    }

    @GetMapping("/listIncomes/{idUser}")
    public List<Income> getAllIncomes(@PathVariable Integer idUser) {
        return incomeService.getAllIncomes(idUser);
    }
}
