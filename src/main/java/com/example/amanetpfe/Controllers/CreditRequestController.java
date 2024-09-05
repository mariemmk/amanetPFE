package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.Credit;
import com.example.amanetpfe.Entities.AmortizationEntry;
import com.example.amanetpfe.Services.Classes.CreditDetails;
import com.example.amanetpfe.Services.Classes.CreditRequestService;
import com.example.amanetpfe.dto.InvestmentResponse;
import com.example.amanetpfe.dto.LoanDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
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

    @PostMapping("/request")
    public ResponseEntity<Credit> createCreditRequest(
            @RequestParam String loanType,
            @RequestParam BigDecimal amount,
            @RequestParam int duration,
            @RequestParam Integer idUser,
            @RequestParam(required = false) Double carPrice,
            @RequestParam(required = false) Integer horsepower,
            @RequestParam(required = false) String employeur,
            @RequestParam(required = false) String addressEmplyeur,
            @RequestParam(required = false) String postOccupe,
            @RequestParam(required = false) BigDecimal revenuMensuels,
            @RequestParam(required = false) String typeContract,
            @RequestParam(required = false) String creditEnCours,
            @RequestParam("file") MultipartFile file) {

        try {
            Credit credit = creditRequestService.createCreditRequest(loanType,
                    amount,
                    duration,
                    idUser,
                    carPrice,
                    horsepower,
                    employeur,
                    addressEmplyeur,
                    postOccupe,
                    revenuMensuels,
                    typeContract,
                    creditEnCours,file);
            return ResponseEntity.ok(credit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }


    @GetMapping("/requests")
    public List<Credit> getAllCreditRequests() {
        return creditRequestService.getAllCreditRequests();
    }



    @GetMapping("/request/{id}/file")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Optional<Credit> optionalCredit = creditRequestService.getCreditRequestById(id);

        if (optionalCredit.isPresent()) {
            Credit credit = optionalCredit.get();
            String filePath = credit.getFilePath();

            if (filePath != null && !filePath.isEmpty()) {
                Resource resource = creditRequestService.loadFileAsResource(filePath);

                String contentType;
                try {
                    contentType = Files.probeContentType(Paths.get(filePath));
                } catch (IOException e) {
                    contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Credit> approveCredit(@PathVariable Long id) {
        Credit approvedCredit = creditRequestService.approveCredit(id);
        return ResponseEntity.ok(approvedCredit);
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<Credit>> getCreditsByUserId(@PathVariable Integer idUser) {
        List<Credit> credits = creditRequestService.getCreditsByUserId(idUser);
        return ResponseEntity.ok(credits);
    }


    @PutMapping("/reject/{id}")
    public ResponseEntity<Credit> rejectCreditRequest(@PathVariable Long id) {
        try {
            Credit rejectedCredit = creditRequestService.rejectCreditRequest(id);
            return new ResponseEntity<>(rejectedCredit, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Return 404 if the credit request was not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other exceptions, return 500 if necessary
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count-by-credit-type")
    public ResponseEntity<Map<String, Integer>> getCountByCreditType() {
        List<Object[]> results = creditRequestService.countCreditsByType();
        Map<String, Integer> countByType = new HashMap<>();
        for (Object[] result : results) {
            countByType.put((String) result[0], ((Number) result[1]).intValue());
        }
        return ResponseEntity.ok(countByType);
    }

    @GetMapping("/count-by-status")
    public ResponseEntity<Map<String, Integer>> getCountByStatus() {
        List<Object[]> results = creditRequestService.countCreditsByStatus();
        Map<String, Integer> countByStatus = new HashMap<>();
        for (Object[] result : results) {
            countByStatus.put((String) result[0], ((Number) result[1]).intValue());
        }
        return ResponseEntity.ok(countByStatus);
    }

    @DeleteMapping("deletcredit/{id}")
    public void deletCredit(@PathVariable("id") Long id){
        creditRequestService.removeCreditRequest(id);

    }
}
