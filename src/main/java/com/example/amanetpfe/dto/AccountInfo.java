package com.example.amanetpfe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {
    private String accountName;
    private Long CIN;
    private BigDecimal accountBalance;
    private String accountNumber;
    private String RIB;




}
