package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.Account;
import com.example.amanetpfe.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository  extends JpaRepository<Account, Integer> {
    Optional<Account> findByUser(User user);
}
