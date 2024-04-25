package com.example.amanetpfe.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Account")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAccount")
    private Integer idAccount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference // Indique que c'est le côté inverse de la relation
    private User user;

    @Column(name = "accountNumber" , unique = true)
    private String accountNumber;

    @Column(name = "RIB" , unique = true)
    private String RIB;



    @Column(name = "balance")
    private double balance;

    @Column(name = "accountType")
    private AccountType accountType;

    @Column(name = "devise")
    private String devise;

    @Column(name = "totSolde")
    private double totSolde;

    @Column(name = "dateSolde")
    private Date dateSolde;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();


}
