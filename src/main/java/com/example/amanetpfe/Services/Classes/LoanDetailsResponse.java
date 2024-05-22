package com.example.amanetpfe.Services.Classes;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoanDetailsResponse {
    private String loanType;
    private double amount;
    private int durationYears;
    private String repaymentFrequency;
    private int numberOfInstallments;
    private double interestRate;
    private double installmentAmount;
}
