package org.example.currencyconversionservice.service;

import lombok.RequiredArgsConstructor;
import org.example.currencyconversionservice.DTO.ExchangeRatesEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class ExchangeRateUpdateEventProducer {
    private final KafkaTemplate<String, ExchangeRatesEvent> kafkaTemplate;

    public void sendEvent(ExchangeRatesEvent event) {
        System.out.println("Sent exchange rates: " + event);
        kafkaTemplate.send("exchange-rate-updates", event);
    }
}
