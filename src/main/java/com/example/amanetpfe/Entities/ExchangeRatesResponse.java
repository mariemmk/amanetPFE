package com.example.amanetpfe.Entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExchangeRatesResponse {
    private String base;
    private Map<String, Double> rates;

}
