package com.example.amanetpfe.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String agence;
    private LocalDate date;
    private String accountNumber;

    private String clientName;
    private String clientCIN;
    private String clientIdNumber;
    private String clientJobStatus;
    private double clientNetSalary;
    private String clientOtherIncomeSources;
    private double clientOtherIncomeAmount;

    private BigDecimal creditAmount;
    private String creditPurpose;
    private String repaymentFrequency;
    private int durationYears;
    private String convention;
    private String conventionName;

    private String repaymentType;
    private double propertyOrConstructionAmount;
    private String status; // PENDING, APPROVED, REJECTED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    @JsonBackReference
    private User user;
}
