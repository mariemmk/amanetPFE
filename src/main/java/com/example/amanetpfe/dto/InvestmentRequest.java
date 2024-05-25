package com.example.amanetpfe.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class InvestmentRequest {
    private double amount;

    private LocalDate issueDate;

    private LocalDate maturityDate;

}
