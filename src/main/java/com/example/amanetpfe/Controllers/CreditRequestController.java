package com.example.amanetpfe.Controllers;


import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.CreditRequestRepository;
import com.example.amanetpfe.Services.Classes.CreditRequestService;
import com.example.amanetpfe.Services.Classes.TransactionService;
import lombok.AllArgsConstructor;
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
    private  final TransactionService transactionService;

    private final CreditRequestRepository creditRequestRepository;

    @PostMapping("/request/{idUser}")
    public Credit createCreditRequest(@RequestBody Credit creditRequest , @PathVariable Integer idUser) {
        return creditRequestService.createCreditRequest(creditRequest , idUser);
    }

    @GetMapping("/requests")
    public List<Credit> getAllCreditRequests() {
        return creditRequestService.getAllCreditRequests();
    }


    @PostMapping("/request/{id}/approve")
    public Credit approveCreditRequest(@PathVariable Long id) {
        return creditRequestService.approveCredit(id);
    }

    @PostMapping("/request/{id}/reject")
    public Credit rejectCreditRequest(@PathVariable Long id) {
        return creditRequestService.rejectCreditRequest(id);
    }

    @PostMapping("/create/{idUser}")
    public ResponseEntity<Credit> createCreditRequest(
            @RequestParam String loanType,
            @RequestParam BigDecimal amount,
            @RequestParam int duration,
            @PathVariable Integer idUser,
            @RequestParam(required = false) Double carPrice,
            @RequestParam(required = false) Integer horsepower)
            {

        try {
            Credit credit = transactionService.createCreditRequest(loanType, amount, duration, idUser, carPrice, horsepower);
            return ResponseEntity.ok(credit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credit> getCreditById(@PathVariable Long id) {
        Credit credit = transactionService.getCreditById(id);
        if (credit != null) {
            // Mettre à jour le tableau d'amortissement du crédit
            credit.setAmortizationSchedule(transactionService.getAmortizationScheduleForCredit(id));
            return ResponseEntity.ok(credit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
