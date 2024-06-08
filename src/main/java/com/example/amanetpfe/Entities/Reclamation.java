package com.example.amanetpfe.Entities;


import javax.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="Reclamation")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reclamationId;

    @Column(name = "Contenu")
    private String Contenu;

    @Column(name = "TypeReclamation")
    private String TypeReclamation;

    @Column(name = "date")

    private LocalDate date;



    @ManyToOne
    private User user;
}
