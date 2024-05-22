package com.example.amanetpfe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @Column(name = "CompteADebite")
    String accountNumber;



    @Column(name = "TypeTransaction")
    String typeTransaction;

    @Column(name = "devise")
    String devise;

    @Column(name = "Montant")
    BigDecimal amount;


    private String status;

}
