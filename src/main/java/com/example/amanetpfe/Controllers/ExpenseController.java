package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.Expense;
import com.example.amanetpfe.Entities.Reclamation;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Services.Interfaces.IExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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

}
