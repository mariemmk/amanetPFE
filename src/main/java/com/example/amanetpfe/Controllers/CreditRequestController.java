package com.example.amanetpfe.Controllers;


import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Services.Classes.CreditRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit")
@AllArgsConstructor
public class CreditRequestController {

    private final CreditRequestService creditRequestService;

    @PostMapping("/request/{idUser}")
    public Credit createCreditRequest(@RequestBody Credit creditRequest , @PathVariable Integer idUser) {
        return creditRequestService.createCreditRequest(creditRequest , idUser);
    }

    @GetMapping("/requests")
    public List<Credit> getAllCreditRequests() {
        return creditRequestService.getAllCreditRequests();
    }

    @GetMapping("/request/{id}")
    public Optional<Credit> getCreditRequestById(@PathVariable Long id) {
        return creditRequestService.getCreditRequestById(id);
    }

    @PostMapping("/request/{id}/approve")
    public Credit approveCreditRequest(@PathVariable Long id) {
        return creditRequestService.approveCredit(id);
    }

    @PostMapping("/request/{id}/reject")
    public Credit rejectCreditRequest(@PathVariable Long id) {
        return creditRequestService.rejectCreditRequest(id);
    }
}
