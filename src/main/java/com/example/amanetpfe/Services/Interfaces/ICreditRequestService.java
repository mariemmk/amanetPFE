package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Credit;

import java.util.List;
import java.util.Optional;

public interface ICreditRequestService {


    Credit createCreditRequest(Credit creditRequest);

    List<Credit> getAllCreditRequests();

    Optional<Credit> getCreditRequestById(Long id);



    Credit approveCredit(Long creditId);

    Credit rejectCreditRequest(Long id);
}
