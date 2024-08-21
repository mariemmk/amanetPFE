package com.example.amanetpfe.Entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.time.LocalDate;

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


    private String status;
    @ManyToOne
    private User user;

    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }
}
