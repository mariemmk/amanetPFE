package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.AmortizationEntry;
import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.CreditRequestRepository;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Services.Interfaces.ICreditRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreditRequestService implements ICreditRequestService {


    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CreditRequestRepository creditRepository;


    @Autowired
    private ResourceLoader resourceLoader;

    private final Path fileStorageLocation;

    @Autowired
    public CreditRequestService(@Value("${file.storage.location}") String fileStorageLocation) {
        this.fileStorageLocation = Paths.get(fileStorageLocation)
                .toAbsolutePath().normalize();
    }

    public Resource loadFileAsResource(String filePath) {
        try {
            Path path = Paths.get(filePath).toAbsolutePath().normalize();
            Resource resource = resourceLoader.getResource("file:" + path.toString());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + filePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not load file " + filePath, e);
        }
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

        // Calculate the maximum credit amount based on car horsepower
        BigDecimal maxCreditAmount;
        if (horsepower == 4) {
            maxCreditAmount = BigDecimal.valueOf(carPrice * 0.80);
        } else if (horsepower >= 5) {
            maxCreditAmount = BigDecimal.valueOf(carPrice * 0.60);
        } else {
            throw new IllegalArgumentException("Invalid car horsepower. It must be 4 or more.");
        }

        BigDecimal monthlyRate = BigDecimal.valueOf(rate / 100 / 12);
        int months = duration * 12;

        // Calculate the monthly payment
        BigDecimal monthlyPayment;
        BigDecimal onePlusMonthlyRatePowMinusMonths = BigDecimal.ONE.add(monthlyRate).pow(months);
        BigDecimal denominator = onePlusMonthlyRatePowMinusMonths.subtract(BigDecimal.ONE);

        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Denominator in monthly payment calculation is zero.");
        }

        monthlyPayment = maxCreditAmount.multiply(monthlyRate)
                .divide(denominator, 2, RoundingMode.HALF_UP);

        return new CreditDetails(maxCreditAmount, monthlyPayment);
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
    public String saveFile(MultipartFile file) {
        try {
            // Define the file storage location
            String fileStorageLocation = "C:/Users/marie/OneDrive/file-storage-amennet/";

            // Create a unique file name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Save the file to the directory
            Path filePath = Paths.get(fileStorageLocation + fileName);
            Files.write(filePath, file.getBytes());

            // Return the path to save in the database
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }
    }

    @Override
    public Credit createCreditRequest(String loanType, BigDecimal amount, int duration, Integer idUser,
                                      Double carPrice, Integer horsepower, String employeur,
                                      String addressEmplyeur, String postOccupe,
                                      BigDecimal revenuMensuels, String typeContract,
                                      String creditEnCours, MultipartFile file) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));

        double rate;
        double monthlyPayment = 0;
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
                monthlyPayment = details.getMonthlyPayment().doubleValue();
                amount = details.getMaxCreditAmount();
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

        // Save the file and get the file path
        String filePath = saveFile(file);
        // Supprimer la redéfinition de la variable monthlyPayment ici

        Credit credit = Credit.builder()
                .loanType(loanType)
                .amount(amount)
                .duration(duration)
                .interestRate(BigDecimal.valueOf(rate))
                .monthlyPayment(BigDecimal.valueOf(monthlyPayment))
                .requestDate(LocalDate.now())
                .status("PENDING")
                .user(user)
                .employeur(employeur)
                .addressEmplyeur(addressEmplyeur)
                .postOccupe(postOccupe)
                .revenuMensuels(revenuMensuels)
                .typeContract(typeContract)
                .creditEnCours(creditEnCours)
                .filePath(filePath)
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
    public void removeCreditRequest(Long id) {
        creditRepository.deleteById(id);

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




    @Override
    public List<Credit> getAllCreditRequests() {
        return creditRepository.findAll();
    }
    @Override
    public Optional<Credit> getCreditRequestById(Long id) {
        return creditRepository.findById(id);
    }



    @Override
    public Credit approveCredit(Long id) {
        Credit credit = creditRepository.findById(id)
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
        return creditRepository.save(credit);
    }
    @Override
    public Credit rejectCreditRequest(Long id) {
        Optional<Credit> optionalCreditRequest = creditRepository.findById(id);
        if (optionalCreditRequest.isPresent()) {
            Credit creditRequest = optionalCreditRequest.get();
            creditRequest.setStatus("REJECTED");
            return creditRepository.save(creditRequest);
        }
        throw new IllegalArgumentException("Credit request not found");
    }

    @Override
    public List<Credit> getCreditsByUserId(Integer idUser) {
        // Vérifier si l'utilisateur existe
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Récupérer tous les crédits liés à cet utilisateur
        return creditRepository.findByUser(user);
    }
    @Override
    public List<Object[]> countCreditsByType() {
        return creditRepository.countCreditsByType();
    }
    @Override
    public List<Object[]> countCreditsByStatus() {
        return creditRepository.countCreditsByStatus();
    }


}