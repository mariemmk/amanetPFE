package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.User;
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

        BigDecimal accountBalance = user.getAccountBalance();

        if (accountBalance == null) {
            accountBalance = BigDecimal.ZERO; // Default to zero if null
        }

        // Perform the approval logic, e.g., updating user account balance
        BigDecimal updatedBalance = accountBalance.add(credit.getAmount());
        user.setAccountBalance(updatedBalance);

        // Save the updated user
        userRepository.save(user);

        // Update the credit status or other fields as necessary
        credit.setStatus("Approved");

        // Save the updated credit
        return creditRequestRepository.save(credit);
    }
    @Override
    public Credit rejectCreditRequest(Long id) {
        Optional<Credit> optionalCreditRequest = creditRequestRepository.findById(id);
        if (optionalCreditRequest.isPresent()) {
            Credit creditRequest = optionalCreditRequest.get();
            creditRequest.setStatus("REJECTED");
            return creditRequestRepository.save(creditRequest);
        }
        throw new IllegalArgumentException("Credit request not found");
    }



}
