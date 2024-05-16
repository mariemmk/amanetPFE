package com.example.amanetpfe.Entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Transaction")
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransaction")
    Integer idTransaction;

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
