package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.BankAccount;
import com.example.amanetpfe.dto.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface accountRequestRepository extends JpaRepository<AccountRequest, Integer> {

    @Query("SELECT ar.status, COUNT(ar) FROM AccountRequest ar GROUP BY ar.status")
    List<Object[]> countAccountRequestsByStatus();
}
