package com.example.amanetpfe.Controllers;


import com.example.amanetpfe.Entities.Credit;
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

    @PostMapping("/request")
    public Credit createCreditRequest(@RequestBody Credit creditRequest) {
        return creditRequestService.createCreditRequest(creditRequest);
    }

    @GetMapping("/requests")
    public List<Credit> getAllCreditRequests() {
        return creditRequestService.getAllCreditRequests();
    }

    @GetMapping("/request/{id}")
    public Optional<Credit> getCreditRequestById(@PathVariable Long id) {
        return creditRequestService.getCreditRequestById(id);
    }

    @PostMapping("/request/{creditId}/approve")
    public Credit approveCreditRequest(@PathVariable Long creditId) {
        return creditRequestService.approveCredit(creditId);
    }

    @PostMapping("/request/{id}/reject")
    public Credit rejectCreditRequest(@PathVariable Long id) {
        return creditRequestService.rejectCreditRequest(id);
    }
}
