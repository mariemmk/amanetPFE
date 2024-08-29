package com.example.amanetpfe.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

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
    @JoinColumn(name = "id_user", referencedColumnName = "idUser", nullable = false)
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Transaction> transactions;


}
