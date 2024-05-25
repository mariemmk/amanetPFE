package com.example.amanetpfe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private double grossReturnAdvance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private double netReturnAdvance;
    private double sourceTaxAdvance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private double grossReturnTerm;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private double netReturnTerm;
    private double sourceTaxTerm;
}
