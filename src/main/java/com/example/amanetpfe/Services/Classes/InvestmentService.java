package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.dto.InvestmentResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class InvestmentService {
    private static final double INTEREST_RATE = 0.08;
    private static final double TAX_RATE = 0.20; // 20% tax rate for example

    public double calculateGrossReturn(double amount, double rate, long days) {
        return amount * rate * days / 365;
    }

    public double calculateNetReturn(double grossReturn) {
        return grossReturn * (1 - TAX_RATE);
    }

    public InvestmentResponse simulateInvestment(double amount, LocalDate issueDate, LocalDate maturityDate) {

        long periodDays = ChronoUnit.DAYS.between(issueDate, maturityDate);

        double grossReturnAdvance = calculateGrossReturn(amount, INTEREST_RATE, periodDays);
        double netReturnAdvance = calculateNetReturn(grossReturnAdvance);
        double sourceTaxAdvance = grossReturnAdvance - netReturnAdvance;

        double grossReturnTerm = calculateGrossReturn(amount, INTEREST_RATE, periodDays);
        double netReturnTerm = calculateNetReturn(grossReturnTerm);
        double sourceTaxTerm = grossReturnTerm - netReturnTerm;

        InvestmentResponse response = new InvestmentResponse();
        response.setInitialAmount(amount);
        response.setInterestRate(INTEREST_RATE * 100);
        response.setIssueDate(issueDate);
        response.setMaturityDate(maturityDate);
        response.setPeriodDays(periodDays);
        response.setGrossReturnAdvance(grossReturnAdvance);
        response.setNetReturnAdvance(netReturnAdvance);
        response.setSourceTaxAdvance(sourceTaxAdvance);
        response.setGrossReturnTerm(grossReturnTerm);
        response.setNetReturnTerm(netReturnTerm);
        response.setSourceTaxTerm(sourceTaxTerm);

        return response;
    }
}
