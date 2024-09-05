package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.BankAccount;
import com.example.amanetpfe.Entities.Transaction;

import com.example.amanetpfe.Repositories.BankAccountRepository;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Repositories.TransactionRepository;
import com.example.amanetpfe.Services.Interfaces.ITransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

@Autowired
private IUserRepository userRepository;

   /* @Override
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
*/
    @Override
    public List<Transaction> retrieveAllTransactions() {

        return transactionRepository.findAll();
    }




    public List<Transaction> getTransactionsByDate(Date date) {
        // Définir le début de la journée (00:00:00.000)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();

        // Définir la fin de la journée (23:59:59.999)
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar.getTime();

        return transactionRepository.findTransactionsByCreatedAtBetween(startDate, endDate);
    }



    @Override
    @Transactional
    public void performTransaction(String accountNumber, BigDecimal amount, String typeTransaction) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Bank Account not found"));

        // Vérifiez le solde du compte pour les transactions de débit
        if ("DEBIT".equalsIgnoreCase(typeTransaction) && bankAccount.getAccountBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Créer une nouvelle transaction
        Transaction transaction = Transaction.builder()
                .bankAccount(bankAccount)
                .amount(amount)
                .typeTransaction(typeTransaction)
                .devise(bankAccount.getAccountType())  // Ajuster si nécessaire
                .status("COMPLETED")  // Modifier le statut si nécessaire
                .build();

        // Enregistrer la transaction
        transactionRepository.save(transaction);

        // Mettre à jour le solde du compte bancaire
        if ("DEBIT".equalsIgnoreCase(typeTransaction)) {
            bankAccount.setAccountBalance(bankAccount.getAccountBalance().subtract(amount));
        } else if ("CREDIT".equalsIgnoreCase(typeTransaction)) {
            bankAccount.setAccountBalance(bankAccount.getAccountBalance().add(amount));
        }

        bankAccountRepository.save(bankAccount);
    }
    @Override
    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findByBankAccount_AccountNumber(accountNumber);
    }

    @Override
    public void deleteTransaction(Long transactionId){
        transactionRepository.deleteById(transactionId);
    }
}