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
    private  BankAccountRepository bankAccountRepository;
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
        Optional<BankAccount> optionalAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());
        if (optionalAccount.isPresent()) {
            BankAccount account = optionalAccount.get();
            BigDecimal newBalance = account.getAccountBalance().add(request.getAmount());
            account.setAccountBalance(newBalance);
            bankAccountRepository.save(account);

            Transaction transaction = new Transaction();
            transaction.setAccountNumber(request.getAccountNumber());
            transaction.setTypeTransaction("Credit");
            transaction.setDevise("TND"); // Replace with actual currency if needed
            transaction.setAmount(request.getAmount());
            transaction.setStatus("Success");
            transactionRepository.save(transaction);

            return new BankAccountResponse("Success", "Account credited successfully");
        }
        return new BankAccountResponse("Failure", "Account not found");
    }

    @Override
    public BankAccountResponse debitAccount(CreditDebitRequest request) {
        Optional<BankAccount> optionalAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());
        if (optionalAccount.isPresent()) {
            BankAccount account = optionalAccount.get();
            if (account.getAccountBalance().compareTo(request.getAmount()) >= 0) {
                BigDecimal newBalance = account.getAccountBalance().subtract(request.getAmount());
                account.setAccountBalance(newBalance);
                bankAccountRepository.save(account);


                Transaction transaction = new Transaction();
                transaction.setAccountNumber(request.getAccountNumber());
                transaction.setTypeTransaction("Credit");
                transaction.setDevise("TND"); // Replace with actual currency if needed
                transaction.setAmount(request.getAmount());
                transaction.setStatus("Success");
                transactionRepository.save(transaction);

                return new BankAccountResponse("Success", "Account debited successfully");
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

            if (sourceAccount.getAccountBalance().compareTo(request.getAmount()) >= 0) {
                BigDecimal newSourceBalance = sourceAccount.getAccountBalance().subtract(request.getAmount());
                sourceAccount.setAccountBalance(newSourceBalance);

                BigDecimal newDestinationBalance = destinationAccount.getAccountBalance().add(request.getAmount());
                destinationAccount.setAccountBalance(newDestinationBalance);

                bankAccountRepository.save(sourceAccount);
                bankAccountRepository.save(destinationAccount);


                // Save transactions
                Transaction sourceTransaction = new Transaction();
                sourceTransaction.setAccountNumber(request.getSourceAccountNumber());
                sourceTransaction.setTypeTransaction("Transfer Out");
                sourceTransaction.setDevise("TND"); // Replace with actual currency if needed
                sourceTransaction.setAmount(request.getAmount());
                sourceTransaction.setStatus("Success");
                transactionRepository.save(sourceTransaction);

                Transaction destinationTransaction = new Transaction();
                destinationTransaction.setAccountNumber(request.getDestinationAccountNumber());
                destinationTransaction.setTypeTransaction("Transfer In");
                destinationTransaction.setDevise("TND"); // Replace with actual currency if needed
                destinationTransaction.setAmount(request.getAmount());
                destinationTransaction.setStatus("Success");
                transactionRepository.save(destinationTransaction);

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
        return optionalBankAccount.map(bankAccount -> bankAccount.getUser().getFirstName() + " " + bankAccount.getUser().getFamilyName()).orElse("Account not found");
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
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByAccountNumber(transactionDto.getAccountNumber());
        if (optionalBankAccount.isEmpty()) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        BankAccount bankAccount = optionalBankAccount.get();
        BigDecimal newBalance = bankAccount.getAccountBalance().add(transactionDto.getAmount());
        bankAccount.setAccountBalance(newBalance);
        bankAccountRepository.save(bankAccount);

        return BankResponse.builder()
                .responseMessage(AccountUtils.TRANSACTION_SUCCESS)
                .responseCode(AccountUtils.TRANSACTION_SUCCESS_CODE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(newBalance)
                        .accountName(bankAccount.getUser().getFirstName() + " " + bankAccount.getUser().getFamilyName())
                        .accountNumber(bankAccount.getAccountNumber())
                        .build())
                .build();
    }

}
