package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.Account;

import com.example.amanetpfe.Services.Classes.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Endpoint pour créer un nouveau compte
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.saveAccount(account);
        if (createdAccount != null) {
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint pour récupérer tous les comptes
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // Endpoint pour récupérer un compte par son ID
    @GetMapping("/{idAccount}")
    public ResponseEntity<Account> getAccountById(@PathVariable("idAccount") Integer idAccount) {
        Optional<Account> account = accountService.getAccountById(idAccount);
        return account.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pour supprimer un compte par son ID
    @DeleteMapping("/{idAccount}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable("idAccount") Integer idAccount) {
        accountService.deleteAccountById(idAccount);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint pour mettre à jour un compte
    @PutMapping("/{idAccount}")
    public ResponseEntity<Account> updateAccount(@PathVariable("idAccount") Integer idAccount, @RequestBody Account updatedAccount) {
        Account account = accountService.updateAccount(idAccount, updatedAccount);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
