package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.BankAccount;
import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.BankAccountRepository;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Repositories.TransactionRepository;
import com.example.amanetpfe.Services.Interfaces.IBankAccountService;
import com.example.amanetpfe.dto.*;
import com.example.amanetpfe.utils.AccountUtils;
import com.example.amanetpfe.utils.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BankAccountService implements IBankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public BankAccount getBankAccountByUser(Integer idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + idUser + " not found"));
        return bankAccountRepository.findByUser(user);
    }

    @Override
    public BankAccountResponse creditAccount(CreditDebitRequest request) {
        return updateAccountBalance(request.getAccountNumber(), request.getAmount(), "Credit");
    }

    @Override
    public BankAccountResponse debitAccount(CreditDebitRequest request) {
        Optional<BankAccount> optionalAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());
        if (optionalAccount.isPresent()) {
            BankAccount account = optionalAccount.get();
            if (account.getAccountBalance().compareTo(request.getAmount()) >= 0) {
                return updateAccountBalance(request.getAccountNumber(), request.getAmount(), "Debit");
            } else {
                return new BankAccountResponse("Failure", "Insufficient funds");
            }
        }
        return new BankAccountResponse("Failure", "Account not found");
    }

    @Override
    public BankAccountResponse transfer(TransferRequest request) {
        Optional<BankAccount> SourceAccount = bankAccountRepository.findByAccountNumber(request.getSourceAccountNumber());
        Optional<BankAccount> DestinationAccount = bankAccountRepository.findByAccountNumber(request.getDestinationAccountNumber());

        if (SourceAccount.isPresent() && DestinationAccount.isPresent()) {
            BankAccount sourceAccount = SourceAccount.get();
            BankAccount destinationAccount = DestinationAccount.get();

            // verifie si l compte had suffisament funds
            if (sourceAccount.getAccountBalance().compareTo(request.getAmount()) >= 0) {
                // debit from source
                updateAccountBalance(sourceAccount.getAccountNumber(), request.getAmount(), "Transfer Out");
                //  credit to destination
                updateAccountBalance(destinationAccount.getAccountNumber(), request.getAmount(), "Transfer In");

                return new BankAccountResponse("Success", "Transfer completed successfully");
            } else {
                return new BankAccountResponse("Failure", "Insufficient funds in source account");
            }
        }
        return new BankAccountResponse("Failure", "Source or destination account not found");
    }


    @Override
    public BankResponse updateAccountBalance(TransactionDto transactionDto) {
        BankAccountResponse accountResponse = updateAccountBalance(
                transactionDto.getAccountNumber(),
                transactionDto.getAmount(),
                transactionDto.getTypeTransaction()
        );

        // BankAccountResponse to BankResponse
        BankResponse bankResponse = new BankResponse();
        bankResponse.setResponseMessage(accountResponse.getMessage());

        if ("Success".equals(accountResponse.getStatus())) {
            bankResponse.setResponseCode(AccountUtils.TRANSACTION_SUCCESS_CODE);
        } else {
            bankResponse.setResponseCode(AccountUtils.TRANSACTION_FAILURE_CODE);  // Define the failure code appropriately
        }
        return bankResponse;
    }


    private BankAccountResponse updateAccountBalance(String accountNumber, BigDecimal amount, String transactionType) {
        Optional<BankAccount> optionalAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            BankAccount account = optionalAccount.get();
            BigDecimal newBalance;

            if ("Debit".equalsIgnoreCase(transactionType)) {
                // solde suffisent
                if (account.getAccountBalance().compareTo(amount) >= 0) {
                    newBalance = account.getAccountBalance().subtract(amount);
                } else {
                    return new BankAccountResponse("Failure", "Insufficient funds");
                }
            } else if ("Credit".equalsIgnoreCase(transactionType) || "Transfer In".equalsIgnoreCase(transactionType)) {
                newBalance = account.getAccountBalance().add(amount);
            } else if ("Transfer Out".equalsIgnoreCase(transactionType)) {
                if (account.getAccountBalance().compareTo(amount) >= 0) {
                    newBalance = account.getAccountBalance().subtract(amount);
                } else {
                    return new BankAccountResponse("Failure", "Insufficient funds for transfer");
                }
            } else {
                return new BankAccountResponse("Failure", "Invalid transaction type");
            }

            // Update account balance
            account.setAccountBalance(newBalance);
            bankAccountRepository.save(account);

            // Save transaction
            saveTransaction(account, amount, transactionType);

            return new BankAccountResponse("Success", transactionType + " transaction completed successfully");
        }
        return new BankAccountResponse("Failure", "Account not found");
    }

    private void saveTransaction(BankAccount account, BigDecimal amount, String transactionType) {
        Transaction transaction = new Transaction();
        transaction.setBankAccount(account);
        transaction.setTypeTransaction(transactionType);
        transaction.setDevise("TND");
        transaction.setAmount(amount);
        transaction.setStatus("Success");
        transactionRepository.save(transaction);
    }
}
