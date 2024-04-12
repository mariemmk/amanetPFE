package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Reclamation;
import com.example.amanetpfe.Repositories.ReclamationRepository;
import com.example.amanetpfe.Services.Interfaces.IReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReclamationServiceImpl implements IReclamationService {
    @Autowired
    private ReclamationRepository reclamationRepository ;

    @Override
    public List<Reclamation> allReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation addReclamtion(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation retrieveReclamation(Integer idReclamation) {
        return reclamationRepository.findById(idReclamation).orElse(null);
    }

    @Override
    public void removeReclamation(Integer idReclamtion) {
        reclamationRepository.deleteById(idReclamtion);

    }

    @Override
    public Reclamation updateReclamation(Reclamation reclamation) {
        Reclamation reclamation1 = this.reclamationRepository.findById(reclamation.getReclamatonId()).orElse(null);
        if (reclamation1 != null) {
            return this.reclamationRepository.save(reclamation);
        }
        return null;
    }
}
