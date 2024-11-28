package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.BankAccount;
import com.example.amanetpfe.dto.*;

public interface IBankAccountService {

    BankAccount getBankAccountByUser(Integer idUser);

    BankAccountResponse creditAccount(CreditDebitRequest request);

    BankAccountResponse debitAccount(CreditDebitRequest request);

    BankAccountResponse transfer(TransferRequest request);



    BankResponse updateAccountBalance(TransactionDto transactionDto);
}
