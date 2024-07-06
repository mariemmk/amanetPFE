package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.BankAccount;
import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.BankAccountRepository;
import com.example.amanetpfe.Repositories.CreditRequestRepository;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Services.Interfaces.ICreditRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CreditRequestService implements ICreditRequestService {


    @Autowired
    private CreditRequestRepository creditRequestRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private EmailSender emailSender;


    @Override
    public Credit createCreditRequest(Credit creditRequest, Integer idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));
        creditRequest.setRequestDate(LocalDate.now());
        creditRequest.setStatus("PENDING");
        creditRequest.setUser(user);
        return creditRequestRepository.save(creditRequest);
    }
    @Override
    public List<Credit> getAllCreditRequests() {
        return creditRequestRepository.findAll();
    }
    @Override
    public Optional<Credit> getCreditRequestById(Long id) {
        return creditRequestRepository.findById(id);
    }


    public Credit approveCredit(Long id) {
        Credit credit = creditRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credit ID: " + id));

        User user = credit.getUser();

        if (user == null) {
            throw new IllegalStateException("Credit has no associated user");
        }

        if (user.getBankAccount() != null ) {
            throw new IllegalStateException("User has no associated bank accounts");
        }

        // Assuming that the first bank account is the one to be credited
        BankAccount bankAccount = user.getBankAccount();

        // Update the bank account balance
        BigDecimal updatedBalance = bankAccount.getAccountBalance().add(credit.getAmount());
        bankAccount.setAccountBalance(updatedBalance);

        // Save the updated bank account
        bankAccountRepository.save(bankAccount);

        // Update the credit status or other fields as necessary
        credit.setStatus("Approved");

        String subject = "Votre Demande de Credit a été approuvée";
        String text = "Cher(e) " + user.getFirstName() + " " + user.getFamilyName() + ",\n\nVotre demande de crédit de " + credit.getAmount() + " a été approuvée.\n\nMerci de votre confiance.";

        emailSender.sendEmail(user.getEmail(), subject, text);

        // Save the updated credit
        return creditRequestRepository.save(credit);
    }




    @Override
    public Credit rejectCreditRequest(Long id) {
        Optional<Credit> optionalCreditRequest = creditRequestRepository.findById(id);
        if (optionalCreditRequest.isPresent()) {
            Credit creditRequest = optionalCreditRequest.get();
            creditRequest.setStatus("REJECTED");

            // Send email notification to the user
            User user = creditRequest.getUser();
            if (user != null) {
                String subject = "Votre demande de crédit a été rejetée";
                String text = "Cher "+" "+ user.getFirstName() + ",\n\nNous sommes désolés de vous informer que votre demande de crédit de " + creditRequest.getAmount() + " a été rejetée.\n\nMerci de votre compréhension.";
                emailSender.sendEmail(user.getEmail(), subject, text);
            }

            return creditRequestRepository.save(creditRequest);
        }
        throw new IllegalArgumentException("Credit request not found");
    }



}
