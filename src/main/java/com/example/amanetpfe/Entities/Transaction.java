package com.example.amanetpfe.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Transaction")

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
    Double Montant;

    @Column(name = "MotifPayment")
    String MotifPayment;

    @Column(name = "DateExecution")
    Date DateExecustion;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    



}
