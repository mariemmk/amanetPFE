package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.AmortizationEntry;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreditDetails {

    private List<AmortizationEntry> amortizationSchedule;

    private BigDecimal maxCreditAmount;
    private BigDecimal monthlyPayment;
    public CreditDetails(BigDecimal maxCreditAmount, BigDecimal monthlyPayment) {
        this.maxCreditAmount = maxCreditAmount;
        this.monthlyPayment = monthlyPayment;
    }

}
