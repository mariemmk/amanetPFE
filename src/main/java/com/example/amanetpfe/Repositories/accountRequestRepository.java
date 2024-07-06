package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.BankAccount;
import com.example.amanetpfe.dto.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface accountRequestRepository extends JpaRepository<AccountRequest, Integer> {
}
