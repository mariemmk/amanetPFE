package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.Income;
import com.example.amanetpfe.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser(User user);
}
