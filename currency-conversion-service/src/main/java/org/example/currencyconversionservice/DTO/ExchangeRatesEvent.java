package org.example.currencyconversionservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRatesEvent {
    @JsonProperty("uahToUsdRate")
    private double uahToUsdRate;
    @JsonProperty("uahToEurRate")
    private double uahToEurRate;
}
