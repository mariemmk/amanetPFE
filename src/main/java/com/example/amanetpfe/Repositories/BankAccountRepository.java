package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.BankAccount;
import com.example.amanetpfe.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount , Integer> {
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    BankAccount findByUser(User user);
}
