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
    public Reclamation retrieveReclamation(Integer idReclamation) {
        return reclamationRepository.findById(idReclamation).orElse(null);
    }

    @Override
    public void removeReclamation(Integer idReclamtion) {
        reclamationRepository.deleteById(idReclamtion);

    }

    @Override
    public Reclamation updateReclamation(Reclamation reclamation) {
        return null;
    }

    @Override
    public Reclamation createReclamation(Reclamation reclamation, Integer idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));
        reclamation.setUser(user);
        return reclamationRepository.save(reclamation);
    }
}
