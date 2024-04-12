package com.example.amanetpfe.Entities;


import javax.persistence.*;

import lombok.*;

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
    @Column(name = "ReclamatonId")
    Integer ReclamatonId ;

    @Column(name = "Contenu")
    private String Contenu;

    @Column(name = "TypeReclamation")
    private String TypeReclamation;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;



    @ManyToOne
    private User user;
}
