package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Income;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface IIncomeService {
     Income addIncome(Income income , Integer idUser);
     List<Income> getAllIncomes(Integer idUser);

    void removeIncome(Long idIncome);

    Income updateIncome(Long idIncome, Income updatedIncome);

    Map<YearMonth, BigDecimal> getMonthlyIncome(Integer idUser);
}
