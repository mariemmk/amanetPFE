package com.example.amanetpfe.Controllers;


import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.Reclamation;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.CreditRequestRepository;
import com.example.amanetpfe.Services.Classes.CreditRequestService;
import com.example.amanetpfe.Services.Classes.InvestmentService;
import com.example.amanetpfe.Services.Classes.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit")
@AllArgsConstructor
public class CreditRequestController {

    private final CreditRequestService creditRequestService;
    private final TransactionService transactionService;
    private  final InvestmentService investmentService;
    @PostMapping("/request/{idUser}")
    public ResponseEntity<Credit> createCreditRequest(@RequestBody Credit creditRequest, @PathVariable Integer idUser) {
        try {
            Credit credit = creditRequestService.createCreditRequest(creditRequest, idUser);
            return ResponseEntity.ok(credit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/requests")
    public List<Credit> getAllCreditRequests() {
        return creditRequestService.getAllCreditRequests();
    }

    @PostMapping("/request/{id}/approve")
    public ResponseEntity<Credit> approveCreditRequest(@PathVariable Long id) {
        Credit credit = creditRequestService.approveCredit(id);
        return ResponseEntity.ok(credit);
    }

    @PostMapping("/request/{id}/reject")
    public ResponseEntity<Credit> rejectCreditRequest(@PathVariable Long id) {
        Credit credit = creditRequestService.rejectCreditRequest(id);
        return ResponseEntity.ok(credit);
    }
    @PostMapping("/create/{idUser}")
    public ResponseEntity<Credit> createCreditRequest(
            @RequestParam String loanType,
            @RequestParam BigDecimal amount,
            @RequestParam int duration,
            @PathVariable Integer idUser,
            @RequestParam(required = false) Double carPrice,
            @RequestParam(required = false) Integer horsepower) {

        try {
            Credit credit = transactionService.createCreditRequest(loanType, amount, duration, idUser, carPrice, horsepower);
            return ResponseEntity.ok(credit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credit> getCreditById(@PathVariable Long id) {
        Credit credit = transactionService.getCreditById(id);
        if (credit != null) {
            credit.setAmortizationSchedule(transactionService.getAmortizationScheduleForCredit(id));
            return ResponseEntity.ok(credit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/removeRequestCredit/{id}")
    ResponseEntity<Credit> removeCreditRequest(@PathVariable("id") Long id) {
        transactionService.removeCreditRequest(id);
        return ResponseEntity.ok().build();
    }
}

