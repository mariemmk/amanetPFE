package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {


    Account saveAccount(Account account);

    // Méthode pour récupérer tous les comptes
    List<Account> getAllAccounts();

    // Méthode pour récupérer un compte par son ID
    Optional<Account> getAccountById(Integer idAccount);

    // Méthode pour supprimer un compte par son ID
    void deleteAccountById(Integer idAccount);

    // Méthode pour mettre à jour un compte
    Account updateAccount(Integer idAccount, Account updatedAccount);

    // Méthode pour générer automatiquement le numéro de compte

    String generateAccountNumber();

    String generateRIB();
}
