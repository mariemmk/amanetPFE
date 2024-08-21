package com.example.amanetpfe.dto;

import com.example.amanetpfe.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReclamationDTO {
    private Integer reclamationId;
    private LocalDate date;
    private String contenu;
    private String typeReclamation;

}
