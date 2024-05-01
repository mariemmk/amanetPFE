package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository  extends JpaRepository<Transaction , Integer> {
}
