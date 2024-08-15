package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.AmortizationEntry;
import com.example.amanetpfe.Services.Classes.CreditDetails;
import com.example.amanetpfe.Services.Classes.CreditRequestService;
import com.example.amanetpfe.dto.InvestmentResponse;
import com.example.amanetpfe.dto.LoanDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/credit")
public class CreditRequestController {
    @Autowired
  private CreditRequestService creditRequestService;


    @GetMapping("preslaire_amenagement")
    public LoanDetailsResponse simulatePreslaireAmenagement(@RequestParam double amount , @RequestParam String loanType, @RequestParam  int duration ) {
        double monthlyPayment = creditRequestService.Preslaire_amenagement( amount , duration , loanType);

        LoanDetailsResponse response = new LoanDetailsResponse();
        response.setLoanType(loanType);
        response.setAmount(amount);
        response.setDurationYears(duration);
        response.setRepaymentFrequency("Mensuel");
        response.setNumberOfInstallments(duration * 12);
        response.setInterestRate(13.5);
        response.setInstallmentAmount(monthlyPayment);

        return response;
    }

    @GetMapping("Auto_invest")
    public LoanDetailsResponse simulateAutoInvest(@RequestParam double amount, @RequestParam int duration, @RequestParam int horsepower) {
        CreditDetails creditDetails = creditRequestService.Auto_invest(amount, duration, horsepower);

        LoanDetailsResponse response = new LoanDetailsResponse();
        response.setLoanType("Auto Invest");
        response.setAmount(amount);
        response.setDurationYears(duration);
        response.setRepaymentFrequency("Mensuel");
        response.setNumberOfInstallments(duration * 12);
        response.setInterestRate(12);
        response.setInstallmentAmount(creditDetails.getMonthlyPayment().doubleValue());
        response.setMaxCreditAmount(creditDetails.getMaxCreditAmount().doubleValue());

        return response;
    }

    @GetMapping("Credim_Watani")
    public LoanDetailsResponse simulateCredimWatani(@RequestParam double amount , @RequestParam  int duration , String loanType ) {
        double monthlyPayment = creditRequestService.Credim_Watani( amount , duration , loanType );

        LoanDetailsResponse response = new LoanDetailsResponse();
        response.setLoanType(loanType);
        response.setAmount(amount);
        response.setDurationYears(duration);
        response.setRepaymentFrequency("Mensuel");
        response.setNumberOfInstallments(duration * 12);
        response.setInterestRate(13);
        response.setInstallmentAmount(monthlyPayment);

        return response;
    }

    @GetMapping("Credim_Express")
    public LoanDetailsResponse simulateCredimExpress(@RequestParam double amount , @RequestParam  int duration ) {
        double monthlyPayment = creditRequestService.Credim_Express( amount , duration );

        LoanDetailsResponse response = new LoanDetailsResponse();
        response.setLoanType("Credim Express");
        response.setAmount(amount);
        response.setDurationYears(duration);
        response.setRepaymentFrequency("Mensuel");
        response.setNumberOfInstallments(duration * 12);
        response.setInterestRate(10.5);
        response.setInstallmentAmount(monthlyPayment);

        return response;
    }



}
