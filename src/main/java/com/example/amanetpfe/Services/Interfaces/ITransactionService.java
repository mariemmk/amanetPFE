package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.dto.TransactionDto;

import java.util.Map;

public interface ITransactionService {

    void saveTransaction(TransactionDto transactionDto);




   double Preslaire_amenagement(double amount, int duration , String loanType);

   double Auto_invest(double amount , int duration);

    double Credim_Watani(double amount, int duration , String loanType);

    double Credim_Express(double amount, int duration);
}
