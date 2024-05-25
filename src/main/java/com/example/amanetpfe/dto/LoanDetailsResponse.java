package com.example.amanetpfe.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoanDetailsResponse {
    private String loanType;
    private double maxCreditAmount;
    private double amount;
    private int durationYears;
    private String repaymentFrequency;
    private int numberOfInstallments;
    private double interestRate;
    private double installmentAmount;







}

