package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.ExchangeRatesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService {
    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    private Map<String, Double> rates = new HashMap<>();

    @Scheduled(fixedRate = 3600000) // Mise à jour toutes les heures
    public void fetchRates() {
        String url = String.format("%s?apikey=%s", apiUrl, apiKey);
        ExchangeRatesResponse response = restTemplate.getForObject(url, ExchangeRatesResponse.class);
        if (response != null && response.getRates() != null) {
            rates = response.getRates();
        }
    }

    public Map<String, Double> getRates() {
        return rates;
    }
}
