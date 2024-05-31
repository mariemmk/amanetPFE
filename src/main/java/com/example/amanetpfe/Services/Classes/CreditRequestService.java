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
    public Credit createCreditRequest(Credit creditRequest ) {

        creditRequest.setDate(LocalDate.now());
        creditRequest.setStatus("PENDING");
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


    @Override
    public Credit approveCredit(Long creditId) {
        Credit credit = creditRequestRepository.findById(creditId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credit ID"));

        if (!"PENDING".equals(credit.getStatus())) {
            throw new IllegalStateException("Credit is not in a pending state");
        }

        credit.setStatus("APPROVED");

        User user = credit.getUser();
        user.setAccountBalance(user.getAccountBalance().add(BigDecimal.valueOf(credit.getCreditAmount())));
        userRepository.save(user);
        creditRequestRepository.save(credit);

        return credit;
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
