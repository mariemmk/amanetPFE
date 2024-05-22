package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Repositories.TransactionRepository;
import com.example.amanetpfe.Services.Interfaces.ITransactionService;
import com.example.amanetpfe.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public double Preslaire_amenagement(double amount, int duration, String loanType) {
        if (duration > 3) {
            throw new IllegalArgumentException("La durée doit être inférieure ou égale à 3 ans.");
        }

        double rate = 13.5;
        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        return monthlyPayment;
    }

    @Override
    public double Auto_invest(double amount, int duration) {

        double rate = 12;

        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        return monthlyPayment;
    }


    @Override
    public double Credim_Watani(double amount, int duration , String loanType ) {

        double rate = 13;

        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        return monthlyPayment;
    }

    @Override
    public double Credim_Express(double amount, int duration  ) {

        double rate = 10.5;

        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        return monthlyPayment;
    }

}
