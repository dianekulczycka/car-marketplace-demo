package org.example.currencyconversionservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.currencyconversionservice.DTO.ExchangeRate;
import org.example.currencyconversionservice.DTO.ExchangeRatesEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExchangeRatesRestTemplate {

    private final RestTemplate restTemplate;

    public ExchangeRatesEvent getExchangeRates() {
        ExchangeRate[] exchangeRates = restTemplate
                .getForObject("https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11",
                        ExchangeRate[].class);

        if (exchangeRates != null) {
            double uahToUsdRate = Double.parseDouble(exchangeRates[1].getSale());
            double uahToEurRate = Double.parseDouble(exchangeRates[0].getSale());
            return new ExchangeRatesEvent(uahToUsdRate, uahToEurRate);
        }

        return null;
    }
}
