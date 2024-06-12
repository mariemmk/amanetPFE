package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Income;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Repositories.IncomeRepository;
import com.example.amanetpfe.Services.Interfaces.IIncomeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService implements  IIncomeService {


    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    IUserRepository userRepository;

    public Income addIncome(Income income , Integer idUser) {
        User user   = userRepository.findById(idUser).orElse(null);
        income.setUser(user);
        return incomeRepository.save(income);
    }

    public List<Income> getAllIncomes(Integer idUser) {
        User user= userRepository.findById(idUser).orElse(null);

        return incomeRepository.findByUser(user);
    }
}
