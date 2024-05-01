package com.example.amanetpfe.dto;

import com.example.amanetpfe.Entities.TypeTransaction;
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
    Long CompteADebite;

    @Column(name = "CompteACredite")
    Long CompteACredite;

    @Column(name = "TypeTransaction")
    TypeTransaction typeTransaction;

    @Column(name = "devise")
    String devise;

    @Column(name = "Montant")
    BigDecimal Montant;

    @Column(name = "MotifPayment")
    String MotifPayment;

    @Column(name = "DateExecution")
    Date DateExecustion;

    private String status;

}
