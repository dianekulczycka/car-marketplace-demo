package org.example.postlistingservice.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.postlistingservice.enums.CarMake;
import org.example.postlistingservice.enums.Currency;
import org.example.postlistingservice.enums.SellingRegion;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PostRequestDto {
    @Min(1980)
    @Max(2025)
    @NotBlank(message = "Car year is mandatory!")
    private Integer carYear;

    @NotBlank(message = "Car make is mandatory!")
    private CarMake carMake;

    @NotBlank(message = "Car model is mandatory!")
    @Size(min = 2, max = 31, message = "Car model should be at least 2 symbols and at most 31 symbols!")
    private String carModel;

    @NotBlank(message = "Region is mandatory!")
    private SellingRegion sellingRegion;

    @NotBlank(message = "Currency is mandatory!")
    private Currency currency;

    @NotBlank(message = "Price is mandatory!")
    private Double price;

    @Size(min = 5, max = 255, message = "Description should be at least 5 symbols and at most 255 symbols!")
    private String description;
}
