package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Services.Classes.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/exchangeRates")
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/exchange")
    public Map<String, Double> getExchangeRates() {
        return exchangeRateService.getRates();
    }
}
