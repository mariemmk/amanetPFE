package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRequestRepository extends JpaRepository<Credit, Long> {
}
