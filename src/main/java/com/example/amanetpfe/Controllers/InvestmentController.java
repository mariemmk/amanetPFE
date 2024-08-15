package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Services.Classes.InvestmentService;
import com.example.amanetpfe.dto.InvestmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
@RestController
@RequestMapping("/api/investment")
public class InvestmentController {
    @Autowired
    private InvestmentService investmentService;

    @GetMapping("/investment")
    public ResponseEntity<InvestmentResponse> simulateInvestment(
            @RequestParam double amount,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate issueDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maturityDate) {

        if (issueDate.isAfter(maturityDate)) {
            return ResponseEntity.badRequest().body(null);
        }

        InvestmentResponse response = investmentService.simulateInvestment(amount, issueDate, maturityDate);
        return ResponseEntity.ok(response);
    }
}
