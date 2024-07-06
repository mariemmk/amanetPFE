package com.example.amanetpfe.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "BankAccount")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAccount")
    Integer idAccount;

    @Column(name = "accountNumber", unique = true)
    String accountNumber;

    @Column(name = "rib")
    String rib;

    @Column(name = "accountBalance")
    BigDecimal accountBalance;

    @Column(name = "accountType")
    String accountType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    @JsonBackReference
    User user;
}
