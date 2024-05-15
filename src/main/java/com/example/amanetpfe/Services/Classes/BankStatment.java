package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@AllArgsConstructor
public class BankStatment {
    private final TransactionRepository transactionRepository;

    /**
     * retrieve list of transaction within a date range given an account number
     * generate a pdf file of transaction
     * send the file via email
     */

  /*  public List<Transaction> generateStatment(String accountNumber , String tartDate , String endDate ){
        List<Transaction> transactionList = transactionRepository.findAll().stream().filter(transaction ->
                transaction.getAcc)

    }*/

}
