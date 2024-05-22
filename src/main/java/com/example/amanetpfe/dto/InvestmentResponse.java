package com.example.amanetpfe.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class InvestmentResponse {
    private double initialAmount;
    private double interestRate;
    private LocalDate issueDate;
    private LocalDate maturityDate;
    private long periodDays;
    private double grossReturnAdvance;
    private double netReturnAdvance;
    private double sourceTaxAdvance;
    private double grossReturnTerm;
    private double netReturnTerm;
    private double sourceTaxTerm;
}
