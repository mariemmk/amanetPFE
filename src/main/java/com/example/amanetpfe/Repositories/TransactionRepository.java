package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.BankAccount;
import com.example.amanetpfe.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionsByCreatedAtBetween(Date startDate, Date endDate);

    List<Transaction> findByBankAccount_AccountNumber(String accountNumber);


}
