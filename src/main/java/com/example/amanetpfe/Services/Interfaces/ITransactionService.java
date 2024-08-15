package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.AmortizationEntry;
import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Services.Classes.CreditDetails;
import com.example.amanetpfe.dto.TransactionDto;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ITransactionService {

    void saveTransaction(TransactionDto transactionDto);


    List<Transaction> retrieveAllTransactions();




}
