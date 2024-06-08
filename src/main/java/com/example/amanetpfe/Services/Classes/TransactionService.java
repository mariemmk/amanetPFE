package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Repositories.TransactionRepository;
import com.example.amanetpfe.Services.Interfaces.ITransactionService;
import com.example.amanetpfe.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    TransactionRepository transactionRepository;



    @Override
    public void saveTransaction(TransactionDto transactionDto) {

        Transaction transaction = Transaction.builder()
                .typeTransaction(transactionDto.getTypeTransaction())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("SUCCESS")
                .devise("TND")
                .build();
        transactionRepository.save(transaction);
        System.out.println("transaction saved successfully");

    }

    @Override
    public List<Transaction> retrieveAllTransactions() {
        return transactionRepository.findAll();
    }


    @Override
    public double Preslaire_amenagement(double amount, int duration, String loanType) {
        if (duration > 3) {
            throw new IllegalArgumentException("La durée doit être inférieure ou égale à 3 ans.");
        }

        double rate = 13.5;
        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        return Math.round(monthlyPayment * 100.0) / 100.0;
    }

    @Override
    public CreditDetails Auto_invest(double carPrice, int duration, int horsepower) {
        double rate = 12;

        // Calculer le montant maximal du crédit en fonction de la puissance de la voiture
        double maxCreditAmount;
        if (horsepower == 4) {
            maxCreditAmount = carPrice * 0.80;
        } else if (horsepower >= 5) {
            maxCreditAmount = carPrice * 0.60;
        } else {
            throw new IllegalArgumentException("Puissance de voiture non valide. La puissance doit être 4 ou plus.");
        }

        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (maxCreditAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        monthlyPayment = Math.round(monthlyPayment * 100.0) / 100.0;
        maxCreditAmount = Math.round(maxCreditAmount * 100.0) / 100.0;

        return new CreditDetails(maxCreditAmount, monthlyPayment);
    }



    @Override
    public double Credim_Watani(double amount, int duration , String loanType ) {

        double rate = 13;

        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        return Math.round(monthlyPayment * 100.0) / 100.0;
    }

    @Override
    public double Credim_Express(double amount, int duration  ) {

        double rate = 10.5;

        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        return Math.round(monthlyPayment * 100.0) / 100.0;
    }

}
