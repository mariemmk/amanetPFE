package com.example.amanetpfe.Entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer idExpense;

    private BigDecimal amount;
    private String category;
    private  String Description;
    private LocalDate date;

    @ManyToOne
    private User user;

}
