package org.example.currencyconversionservice.service;

import lombok.RequiredArgsConstructor;
import org.example.currencyconversionservice.DTO.ExchangeRatesEvent;
import org.example.currencyconversionservice.service.impl.ExchangeRatesRestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateScheduler {
    private final ExchangeRatesRestTemplate exchangeRatesRestTemplate;
    private final ExchangeRateUpdateEventProducer exchangeRateUpdateEventProducer;

    @Scheduled(cron = "0 * * * * *")
    public void updateExchangeRates() {
        ExchangeRatesEvent event = exchangeRatesRestTemplate.getExchangeRates();
        exchangeRateUpdateEventProducer.sendEvent(event);
    }
}
