package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRequestRepository extends JpaRepository<Credit, Long> {

    @Query("SELECT c.loanType, COUNT(c) FROM Credit c GROUP BY c.loanType")
    List<Object[]> countCreditsByType();

    @Query("SELECT c.status, COUNT(c) FROM Credit c GROUP BY c.status")
    List<Object[]> countCreditsByStatus();
}
