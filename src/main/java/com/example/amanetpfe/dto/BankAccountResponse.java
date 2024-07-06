package com.example.amanetpfe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankAccountResponse {
    private String status;
    private String message;
}
