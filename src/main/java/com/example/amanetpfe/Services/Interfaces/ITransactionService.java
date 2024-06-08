package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Services.Classes.CreditDetails;
import com.example.amanetpfe.dto.TransactionDto;

import java.util.List;
import java.util.Map;

public interface ITransactionService {

    void saveTransaction(TransactionDto transactionDto);


    List<Transaction> retrieveAllTransactions();

   double Preslaire_amenagement(double amount, int duration , String loanType);

   CreditDetails Auto_invest(double amount , int duration, int horspower);

    double Credim_Watani(double amount, int duration , String loanType);

    double Credim_Express(double amount, int duration);
}
