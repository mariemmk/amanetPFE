package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Income;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Repositories.IncomeRepository;
import com.example.amanetpfe.Services.Interfaces.IIncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IncomeService implements  IIncomeService {


    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    IUserRepository userRepository;


    @Override
    public Income addIncome(Income income , Integer idUser) {
        User user   = userRepository.findById(idUser).orElse(null);
        income.setUser(user);
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> getAllIncomes(Integer idUser) {
        User user= userRepository.findById(idUser).orElse(null);

        return incomeRepository.findByUser(user);
    }
    @Override
    public void removeIncome(Long idIncome) {
        incomeRepository.deleteById(idIncome);

    }
    @Override
    public Income updateIncome(Long idIncome, Income updatedIncome) {
        // Find the existing income by its ID
        Income existingIncome = incomeRepository.findById(idIncome).orElse(null);

        if (existingIncome != null) {
            // Update the existing income details with the new values
            existingIncome.setAmount(updatedIncome.getAmount());
            existingIncome.setCategory(updatedIncome.getCategory());
            existingIncome.setDate(updatedIncome.getDate());
            // Add more fields to update as necessary

            // Save the updated income back to the repository
            return incomeRepository.save(existingIncome);
        }

        // Return null or throw an exception if the income is not found
        return null;
    }

    @Override
    public Map<YearMonth, BigDecimal> getMonthlyIncome(Integer idUser) {
        List<Income> incomes = getAllIncomes(idUser);
        Map<YearMonth, BigDecimal> monthlyIncome = new HashMap<>();

        for (Income income : incomes) {
            try {
                if (income.getDate() != null) {
                    YearMonth yearMonth = YearMonth.from(income.getDate());
                    monthlyIncome.merge(yearMonth, income.getAmount(), BigDecimal::add);
                } else {
                    System.err.println("Expense date is null");
                }
            } catch (Exception e) {
                System.err.println("Error processing expense: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return monthlyIncome;
    }

}
