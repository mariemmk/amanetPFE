package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BankStatment {
    private final TransactionRepository transactionRepository;

    /**
     * retrieve list of transaction within a date range given an account number
     * generate a pdf file of transaction
     * send the file via email
     */

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter).plusDays(1); // Add 1 day to include transactions on the end date

        List<Transaction> transactionList = transactionRepository.findAll().stream()
              //  .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt().toLocalDateTime().isAfter(start.atStartOfDay()))
                .filter(transaction -> transaction.getCreatedAt().toLocalDateTime().isBefore(end.atStartOfDay()))
                .collect(Collectors.toList());

        return transactionList;
    }

}
