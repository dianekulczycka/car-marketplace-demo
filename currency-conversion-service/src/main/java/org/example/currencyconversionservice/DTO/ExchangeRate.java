package org.example.currencyconversionservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExchangeRate {
    private String ccy;
    private String base_ccy;
    private String buy;
    private String sale;
}
