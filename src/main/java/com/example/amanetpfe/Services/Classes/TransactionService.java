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
                .CompteADebite(transactionDto.getCompteADebite())
                .CompteACredite(transactionDto.getCompteACredite())
                .Montant(transactionDto.getMontant())
                .status("SUCCESS")
                .devise("TND")
                .MotifPayment(transactionDto.getMotifPayment())
                .DateExecustion(transactionDto.getDateExecustion())
                .build();
        transactionRepository.save(transaction);
        System.out.println("transaction saved successfully");

    }
}
