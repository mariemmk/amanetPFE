package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.dto.TransactionDto;

public interface ITransactionService {

    void saveTransaction(TransactionDto transactionDto);
}
