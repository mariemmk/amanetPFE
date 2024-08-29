package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.*;
import com.example.amanetpfe.Services.Classes.CreditDetails;
import com.example.amanetpfe.dto.TransactionDto;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ITransactionService {

    //  void saveTransaction(TransactionDto transactionDto);


    List<Transaction> retrieveAllTransactions();


    List<Transaction> getTransactionsByDate(Date date);

    //  List<Transaction> getTransactionByAccountNumber(String AccountNumber);

    @Transactional
    void performTransaction(String accountNumber, BigDecimal amount, String typeTransaction);

    List<Transaction> getTransactionsByAccountNumber(String accountNumber);


}