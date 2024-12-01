package org.example.postlistingservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.currencyconversionservice.DTO.ExchangeRatesEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateUpdateEventConsumer {
    private final ExchangeRatesService exchangeRatesService;

    @KafkaListener(topics = "exchange-rate-updates", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(ExchangeRatesEvent event) {
        exchangeRatesService.updateRates(event.getUahToUsdRate(), event.getUahToEurRate());
        log.info("Received exchange-rate-updates event: {}", event);
    }
}
