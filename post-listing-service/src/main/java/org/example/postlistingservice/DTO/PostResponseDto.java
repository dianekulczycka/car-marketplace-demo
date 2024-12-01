package org.example.postlistingservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.postlistingservice.enums.CarMake;
import org.example.postlistingservice.enums.SellingRegion;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PostResponseDto {
    private Long postId;
    private String userEmail;
    private Integer carYear;
    private CarMake carMake;
    private String carModel;
    private SellingRegion sellingRegion;
    private Double price;
    private String description;
}
