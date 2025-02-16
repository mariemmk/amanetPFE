package com.example.amanetpfe.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "AmortizationEntry")
public class AmortizationEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int month;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal remainingBalance;
    private BigDecimal monthlyPayment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    @JsonIgnore
    private Credit credit;

    // New constructor including monthlyPayment
    public AmortizationEntry(int month, BigDecimal principal, BigDecimal interest, BigDecimal remainingBalance, BigDecimal monthlyPayment, Credit credit) {
        this.month = month;
        this.principal = principal;
        this.interest = interest;
        this.remainingBalance = remainingBalance;
        this.monthlyPayment = monthlyPayment;
        this.credit = credit;
    }
}
