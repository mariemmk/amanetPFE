package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.BankAccount;
import com.example.amanetpfe.Services.Classes.UserServiceImpl;
import com.example.amanetpfe.Services.Interfaces.IBankAccountService;
import com.example.amanetpfe.dto.*;
import com.example.amanetpfe.utils.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/BankAccount")
@CrossOrigin(origins = "*")
public class BankAccountController {

    private final IBankAccountService iBankAccountService;
    private final UserServiceImpl userService;

    public BankAccountController(IBankAccountService iBankAccountService, UserServiceImpl userService) {
        this.iBankAccountService = iBankAccountService;
        this.userService = userService;
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<BankAccount> getBankAccountByUser(@PathVariable Integer idUser) {
        try {
            BankAccount bankAccount = iBankAccountService.getBankAccountByUser(idUser);
            return ResponseEntity.ok(bankAccount);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/credit")
    public ResponseEntity<BankAccountResponse> creditAccount(@RequestBody CreditDebitRequest request) {
        BankAccountResponse response = iBankAccountService.creditAccount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/debit")
    public ResponseEntity<BankAccountResponse> debitAccount(@RequestBody CreditDebitRequest request) {
        BankAccountResponse response = iBankAccountService.debitAccount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<BankAccountResponse> transfer(@RequestBody TransferRequest request) {
        BankAccountResponse response = iBankAccountService.transfer(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/balanceEnquiry")
    public ResponseEntity<BankResponse> balanceEnquiry(@RequestBody EnquiryRequest request) {
        BankResponse response = iBankAccountService.balanceEnquiry(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nameEnquiry")
    public ResponseEntity<String> nameEnquiry(@RequestBody EnquiryRequest request) {
        String response = iBankAccountService.nameEnquiry(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account-requests/count-by-status")
    public Map<String, Long> getAccountRequestCountByStatus() {
        return userService.getAccountRequestCountByStatus();
    }
}
