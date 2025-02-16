package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Reclamation;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Repositories.ReclamationRepository;
import com.example.amanetpfe.Services.Interfaces.IReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReclamationServiceImpl implements IReclamationService {
    @Autowired
    private ReclamationRepository reclamationRepository ;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<Reclamation> allReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation addReclamtion(Reclamation reclamation ) {
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation retrieveReclamation(Integer reclamationId) {
        return reclamationRepository.findById(reclamationId).orElse(null);
    }

    @Override
    public void removeReclamation(Integer reclamationId) {
        reclamationRepository.deleteById(reclamationId);

    }

    @Override
    public Reclamation updateReclamation(Reclamation reclamation) {
        return null;
    }

    @Override
    public Reclamation createReclamation(Reclamation reclamation, Integer idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));

        reclamation.setUser(user);
reclamation.setStatus("not treated");
        return reclamationRepository.save(reclamation);
    }
}
