package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Reclamation;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IReclamationService {
    List<Reclamation> allReclamations() ;

    Reclamation addReclamtion(Reclamation reclamation);

    Reclamation retrieveReclamation(Integer idReclamation);

    void removeReclamation(Integer idReclamtion);

    Reclamation updateReclamation(Reclamation reclamation);
}
