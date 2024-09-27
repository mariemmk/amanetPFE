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
        Optional<BankAccount> optionalSourceAccount = bankAccountRepository.findByAccountNumber(request.getSourceAccountNumber());
        Optional<BankAccount> optionalDestinationAccount = bankAccountRepository.findByAccountNumber(request.getDestinationAccountNumber());

        if (optionalSourceAccount.isPresent() && optionalDestinationAccount.isPresent()) {
            BankAccount sourceAccount = optionalSourceAccount.get();
            BankAccount destinationAccount = optionalDestinationAccount.get();

            // Check if source account has sufficient funds
            if (sourceAccount.getAccountBalance().compareTo(request.getAmount()) >= 0) {
                // Perform debit from source account
                updateAccountBalance(sourceAccount.getAccountNumber(), request.getAmount(), "Transfer Out");
                // Perform credit to destination account
                updateAccountBalance(destinationAccount.getAccountNumber(), request.getAmount(), "Transfer In");

                return new BankAccountResponse("Success", "Transfer completed successfully");
            } else {
                return new BankAccountResponse("Failure", "Insufficient funds in source account");
            }
        }
        return new BankAccountResponse("Failure", "Source or destination account not found");
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());
        return optionalBankAccount.map(bankAccount -> bankAccount.getUser().getFirstName() + " " + bankAccount.getUser().getFamilyName())
                .orElse("Account not found");
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());
        if (optionalBankAccount.isEmpty()) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        BankAccount bankAccount = optionalBankAccount.get();
        return BankResponse.builder()
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(bankAccount.getAccountBalance())
                        .accountName(bankAccount.getUser().getFirstName() + " " + bankAccount.getUser().getFamilyName())
                        .accountNumber(bankAccount.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse updateAccountBalance(TransactionDto transactionDto) {
        BankAccountResponse accountResponse = updateAccountBalance(
                transactionDto.getAccountNumber(),
                transactionDto.getAmount(),
                transactionDto.getTypeTransaction()
        );

        // Convert BankAccountResponse to BankResponse
        BankResponse bankResponse = new BankResponse();
        bankResponse.setResponseMessage(accountResponse.getMessage());

        if ("Success".equals(accountResponse.getStatus())) {
            bankResponse.setResponseCode(AccountUtils.TRANSACTION_SUCCESS_CODE);
        } else {
            bankResponse.setResponseCode(AccountUtils.TRANSACTION_FAILURE_CODE);  // Define the failure code appropriately
        }

        // If needed, map additional fields such as account information
        return bankResponse;
    }


    private BankAccountResponse updateAccountBalance(String accountNumber, BigDecimal amount, String transactionType) {
        Optional<BankAccount> optionalAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            BankAccount account = optionalAccount.get();
            BigDecimal newBalance;

            if ("Debit".equalsIgnoreCase(transactionType)) {
                // Ensure sufficient funds for debit transactions
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
