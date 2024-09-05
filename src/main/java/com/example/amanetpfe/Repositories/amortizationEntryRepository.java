package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.AmortizationEntry;
import com.example.amanetpfe.dto.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface amortizationEntryRepository extends JpaRepository<AmortizationEntry, Long> {
    List<AmortizationEntry> findByCreditId(Long id);
}
