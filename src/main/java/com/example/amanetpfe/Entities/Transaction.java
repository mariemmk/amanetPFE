package com.example.amanetpfe.Entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "transaction")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long transactionId;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "type_transaction")
    private String typeTransaction;

    @Column(name = "devise")
    private String devise;

    @Column(name = "montant")
    private BigDecimal amount;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
