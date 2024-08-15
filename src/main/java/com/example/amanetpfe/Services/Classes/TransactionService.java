package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.AmortizationEntry;
import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Entities.User;

import com.example.amanetpfe.Repositories.CreditRequestRepository;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Repositories.TransactionRepository;
import com.example.amanetpfe.Services.Interfaces.ITransactionService;

import com.example.amanetpfe.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;



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





}
