package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Services.Classes.BankStatment;
import com.example.amanetpfe.Services.Classes.CreditDetails;
import com.example.amanetpfe.Services.Classes.InvestmentService;
import com.example.amanetpfe.Services.Interfaces.ICreditRequestService;
import com.example.amanetpfe.Services.Interfaces.ITransactionService;
import com.example.amanetpfe.dto.LoanDetailsResponse;
import com.example.amanetpfe.Services.Classes.TransactionService;
import com.example.amanetpfe.dto.InvestmentResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class TransactionController {
    private BankStatment bankStatment;
    private ITransactionService transactionService;
  private ICreditRequestService creditRequestService;

    private  InvestmentService investmentService;


    @GetMapping("historique")
    public List<Transaction> generateBankStatment(@RequestParam String accountNumber , @RequestParam String startDate , @RequestParam String endDate){
        return  bankStatment.generateStatement(accountNumber,startDate,endDate);

    }


    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.retrieveAllTransactions();
    }


    @GetMapping("simulate_Placement")
    public InvestmentResponse simulateInvestment(@RequestParam  double amount , @RequestParam LocalDate issueDate , @RequestParam LocalDate maturityDate){
        return investmentService.simulateInvestment(amount ,issueDate , maturityDate);
    }

}
