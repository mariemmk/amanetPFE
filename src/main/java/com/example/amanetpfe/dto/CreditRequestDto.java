package com.example.amanetpfe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequestDto {
    private String loanType;
    private BigDecimal amount;
    private int duration;
    private Integer idUser;

}
