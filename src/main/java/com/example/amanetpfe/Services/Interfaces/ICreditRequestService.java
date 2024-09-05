package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.AmortizationEntry;
import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Services.Classes.CreditDetails;
import com.example.amanetpfe.dto.CreditRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICreditRequestService {


    double Preslaire_amenagement(double amount, int duration, String loanType);

    CreditDetails Auto_invest(double carPrice, int duration, int horsepower);

    double Credim_Watani(double amount, int duration, String loanType);

    double Credim_Express(double amount, int duration);


    Credit createCreditRequest(String loanType, BigDecimal amount, int duration, Integer idUser,
                               Double carPrice, Integer horsepower, String employeur,
                               String addressEmplyeur, String postOccupe,
                               BigDecimal revenuMensuels, String typeContract,
                               String creditEnCours, MultipartFile file);

    void removeCreditRequest(Long id);

    List<AmortizationEntry> getAmortizationScheduleForCredit(Long id);

    List<Credit> getAllCreditRequests();

    Optional<Credit> getCreditRequestById(Long id);

  //  Credit approveCredit(Long id);

    Credit approveCredit(Long id);

    Credit rejectCreditRequest(Long id);

    List<Credit> getCreditsByUserId(Integer idUser);

    List<Object[]> countCreditsByType();

    // Method to count credits by status
    List<Object[]> countCreditsByStatus();
}
