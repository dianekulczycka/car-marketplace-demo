package org.example.postlistingservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.postlistingservice.enums.SellingRegion;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AveragePriceResponseDto {
    private SellingRegion sellingRegion;
    private double averagePrice;
}
