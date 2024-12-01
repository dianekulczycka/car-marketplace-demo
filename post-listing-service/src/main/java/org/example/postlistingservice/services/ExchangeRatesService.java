package org.example.postlistingservice.services;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ExchangeRatesService {
    private double uahToUsdRate = 41.84100;
    private double uahToEurRate = 44.24779;

    public void updateRates(double uahToUsdRate, double uahToEurRate) {
        this.uahToUsdRate = uahToUsdRate;
        this.uahToEurRate = uahToEurRate;
    }
}
