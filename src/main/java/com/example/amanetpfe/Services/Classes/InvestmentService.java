package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.dto.InvestmentResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class InvestmentService {
    private static final double INTEREST_RATE = 0.08;
    private static final double TAX_RATE = 0.20; // 20% tax rate for example

    public double calculateGrossReturn(double amount, double rate, long days) {
        double grossReturn = amount * rate * days / 365;
        return roundToTwoDecimals(grossReturn);
    }

    public double calculateNetReturn(double grossReturn) {
        double netReturn = grossReturn * (1 - TAX_RATE);
        return roundToTwoDecimals(netReturn);
    }

    private double roundToTwoDecimals(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public InvestmentResponse simulateInvestment(double amount, LocalDate issueDate, LocalDate maturityDate) {

        long periodDays = ChronoUnit.DAYS.between(issueDate, maturityDate);

        double grossReturnAdvance = calculateGrossReturn(amount, INTEREST_RATE, periodDays);
        double netReturnAdvance = calculateNetReturn(grossReturnAdvance);


        double grossReturnTerm = calculateGrossReturn(amount, INTEREST_RATE, periodDays);
        double netReturnTerm = calculateNetReturn(grossReturnTerm);

        double sourceTaxAdvance = roundToTwoDecimals(grossReturnAdvance - netReturnAdvance);
        double sourceTaxTerm = roundToTwoDecimals(grossReturnTerm - netReturnTerm);

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
