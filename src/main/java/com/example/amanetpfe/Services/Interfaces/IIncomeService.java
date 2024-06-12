package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.Income;

import java.util.List;

public interface IIncomeService {
     Income addIncome(Income income , Integer idUser);
     List<Income> getAllIncomes(Integer idUser);
}
