package com.example.amanetpfe.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Credit")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanType;
    private BigDecimal amount;
    private int duration;
    private BigDecimal interestRate;
    private BigDecimal monthlyPayment;
    private LocalDate requestDate;
    private String status; // PENDING, APPROVED, REJECTED
    private String employeur;
    private String addressEmplyeur;
    private String postOccupe;
    private BigDecimal revenuMensuels;
    private String typeContract;
    private String creditEnCours;
    private String filePath;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)

    private User user;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
      // Ignore this field in certain contexts
    private List<AmortizationEntry> amortizationSchedule;


}
