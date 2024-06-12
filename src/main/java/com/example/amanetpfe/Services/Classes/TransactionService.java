package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.AmortizationEntry;
import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.Transaction;
import com.example.amanetpfe.Entities.User;

import com.example.amanetpfe.Repositories.CreditRequestRepository;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Repositories.TransactionRepository;
import com.example.amanetpfe.Services.Interfaces.ITransactionService;

import com.example.amanetpfe.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CreditRequestRepository creditRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .typeTransaction(transactionDto.getTypeTransaction())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("SUCCESS")
                .devise("TND")
                .build();
        transactionRepository.save(transaction);
        System.out.println("transaction saved successfully");
    }

    @Override
    public List<Transaction> retrieveAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public double Preslaire_amenagement(double amount, int duration, String loanType) {
        if (duration > 3) {
            throw new IllegalArgumentException("La durée doit être inférieure ou égale à 3 ans.");
        }

        double rate = 13.5;
        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        return Math.round(monthlyPayment * 100.0) / 100.0;
    }

    @Override
    public CreditDetails Auto_invest(double carPrice, int duration, int horsepower) {
        double rate = 12;

        // Calculer le montant maximal du crédit en fonction de la puissance de la voiture
        double maxCreditAmount;
        if (horsepower == 4) {
            maxCreditAmount = carPrice * 0.80;
        } else if (horsepower >= 5) {
            maxCreditAmount = carPrice * 0.60;
        } else {
            throw new IllegalArgumentException("Puissance de voiture non valide. La puissance doit être 4 ou plus.");
        }

        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (maxCreditAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        monthlyPayment = Math.round(monthlyPayment * 100.0) / 100.0;
        maxCreditAmount = Math.round(maxCreditAmount * 100.0) / 100.0;

        return new CreditDetails(BigDecimal.valueOf(maxCreditAmount), BigDecimal.valueOf(monthlyPayment));
    }

    @Override
    public double Credim_Watani(double amount, int duration, String loanType) {
        double rate = 13;
        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        return Math.round(monthlyPayment * 100.0) / 100.0;
    }

    @Override
    public double Credim_Express(double amount, int duration) {
        double rate = 10.5;
        double monthlyRate = (rate / 100) / 12;
        int months = duration * 12;
        double monthlyPayment = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        return Math.round(monthlyPayment * 100.0) / 100.0;
    }

    private List<AmortizationEntry> createAmortizationSchedule(BigDecimal principal, double annualRate, int durationYears) {
        List<AmortizationEntry> schedule = new ArrayList<>();
        double monthlyRate = annualRate / 12 / 100;
        int numberOfPayments = durationYears * 12;
        double remainingBalance = principal.doubleValue();

        double monthlyPayment = (principal.doubleValue() * monthlyRate) /
                (1 - Math.pow(1 + monthlyRate, -numberOfPayments));

        for (int month = 1; month <= numberOfPayments; month++) {
            double interestPayment = remainingBalance * monthlyRate;
            double principalPayment = monthlyPayment - interestPayment;
            remainingBalance -= principalPayment;

            schedule.add(new AmortizationEntry(
                    month,
                    BigDecimal.valueOf(principalPayment),
                    BigDecimal.valueOf(interestPayment),
                    BigDecimal.valueOf(remainingBalance),
                    null
            ));
        }

        return schedule;
    }

    @Override
    public Credit createCreditRequest(String loanType, BigDecimal amount, int duration, Integer idUser, Double carPrice, Integer horsepower) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));

        double rate;
        double monthlyPayment;
        List<AmortizationEntry> amortizationEntries = null;

        switch (loanType) {
            case "Preslaire_amenagement":
                if (duration > 3) {
                    throw new IllegalArgumentException("La durée doit être inférieure ou égale à 3 ans.");
                }
                rate = 13.5;
                monthlyPayment = Preslaire_amenagement(amount.doubleValue(), duration, loanType);
                amortizationEntries = createAmortizationSchedule(amount, rate, duration);
                break;

            case "Auto_invest":
                if (carPrice == null || horsepower == null) {
                    throw new IllegalArgumentException("Les paramètres carPrice et horsepower sont obligatoires pour Auto_invest.");
                }
                CreditDetails details = Auto_invest(carPrice, duration, horsepower);
                rate = 12;
                monthlyPayment = details.getMonthlyPayment();
                amount = BigDecimal.valueOf(details.getMaxCreditAmount());
                amortizationEntries = createAmortizationSchedule(amount, rate, duration);
                break;

            case "Credim_Watani":
                rate = 13;
                monthlyPayment = Credim_Watani(amount.doubleValue(), duration, loanType);
                amortizationEntries = createAmortizationSchedule(amount, rate, duration);
                break;

            case "Credim_Express":
                rate = 10.5;
                monthlyPayment = Credim_Express(amount.doubleValue(), duration);
                amortizationEntries = createAmortizationSchedule(amount, rate, duration);
                break;

            default:
                throw new IllegalArgumentException("Type de crédit non valide.");
        }

        Credit credit = Credit.builder()
                .loanType(loanType)
                .amount(amount)
                .duration(duration)
                .interestRate(BigDecimal.valueOf(rate))
                .monthlyPayment(BigDecimal.valueOf(monthlyPayment))
                .requestDate(LocalDate.now())
                .status("PENDING")
                .user(user)
                .build();

        List<AmortizationEntry> amortizationSchedule = amortizationEntries.stream()
                .map(entry -> new AmortizationEntry(
                        entry.getMonth(),
                        entry.getPrincipal(),
                        entry.getInterest(),
                        entry.getRemainingBalance(),
                        credit
                ))
                .collect(Collectors.toList());

        credit.setAmortizationSchedule(amortizationSchedule);

        return creditRepository.save(credit);
    }
    @Override
    public List<AmortizationEntry> getAmortizationScheduleForCredit(Long id) {
        Credit credit = creditRepository.findById(id).orElse(null);
        if (credit != null && credit.getAmortizationSchedule() != null) {
            return credit.getAmortizationSchedule();
        } else {
            throw new IllegalArgumentException("Crédit non trouvé ou tableau d'amortissement non disponible.");
        }
    }


    public Credit getCreditById(Long id) { return creditRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Crédit non trouvé avec l'ID : " + id));
    }
}
