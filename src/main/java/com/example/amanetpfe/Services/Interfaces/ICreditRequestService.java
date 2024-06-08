package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.User;

import java.util.List;
import java.util.Optional;

public interface ICreditRequestService {


    Credit createCreditRequest(Credit creditRequest , Integer idUser);

    List<Credit> getAllCreditRequests();

    Optional<Credit> getCreditRequestById(Long id);



    Credit approveCredit(Long id);

    Credit rejectCreditRequest(Long id);
}
