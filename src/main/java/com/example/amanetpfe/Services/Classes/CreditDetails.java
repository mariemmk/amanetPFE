package com.example.amanetpfe.Services.Classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditDetails {
    private final double maxCreditAmount;
    private final double monthlyPayment;

    public CreditDetails(double maxCreditAmount, double monthlyPayment) {
        this.maxCreditAmount = maxCreditAmount;
        this.monthlyPayment = monthlyPayment;
    }

    public double getMaxCreditAmount() {
        return maxCreditAmount;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }
}
