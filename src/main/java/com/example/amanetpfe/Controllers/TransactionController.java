package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Services.Classes.BankStatment;
import com.example.amanetpfe.Services.Classes.InvestmentService;
import com.example.amanetpfe.Services.Classes.LoanDetailsResponse;
import com.example.amanetpfe.Services.Classes.TransactionService;
import com.example.amanetpfe.dto.InvestmentRequest;
import com.example.amanetpfe.dto.InvestmentResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class TransactionController {
    private BankStatment bankStatment;
    private TransactionService transactionService;

    private  InvestmentService investmentService;

    @GetMapping("historique")
    public List<Transaction> generateBankStatment(@RequestParam String accountNumber , @RequestParam String startDate , @RequestParam String endDate){
        return  bankStatment.generateStatement(accountNumber,startDate,endDate);

    }

    @PostMapping("preslaire_amenagement")
    public LoanDetailsResponse simulatePreslaireAmenagement(@RequestParam double amount , @RequestParam String loanType, @RequestParam  int duration ) {
        double monthlyPayment = transactionService.Preslaire_amenagement( amount , duration , loanType);

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


    @PostMapping("Auto_invest")
    public LoanDetailsResponse simulateAutoInvest(@RequestParam double amount , @RequestParam  int duration ) {
        double monthlyPayment = transactionService.Auto_invest( amount , duration );

        LoanDetailsResponse response = new LoanDetailsResponse();
        response.setLoanType("Auto Invest");
        response.setAmount(amount);
        response.setDurationYears(duration);
        response.setRepaymentFrequency("Mensuel");
        response.setNumberOfInstallments(duration * 12);
        response.setInterestRate(12);
        response.setInstallmentAmount(monthlyPayment);

        return response;
    }

    @PostMapping("Credim_Watani")
    public LoanDetailsResponse simulateCredimWatani(@RequestParam double amount , @RequestParam  int duration , String loanType ) {
        double monthlyPayment = transactionService.Credim_Watani( amount , duration , loanType );

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

    @PostMapping("Credim_Express")
    public LoanDetailsResponse simulateCredimExpress(@RequestParam double amount , @RequestParam  int duration ) {
        double monthlyPayment = transactionService.Credim_Express( amount , duration );

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

    @PostMapping("simulate_Placement")
    public InvestmentResponse simulateInvestment(@RequestBody InvestmentRequest request){
        return investmentService.simulateInvestment(request.getAmount() , request.getIssueDate() , request.getMaturityDate());
    }

}
