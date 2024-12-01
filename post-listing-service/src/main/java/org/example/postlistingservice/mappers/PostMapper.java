package org.example.postlistingservice.mappers;

import lombok.RequiredArgsConstructor;
import org.example.postlistingservice.DTO.PostRequestDto;
import org.example.postlistingservice.DTO.PostResponseDto;
import org.example.postlistingservice.entities.Post;
import org.example.postlistingservice.enums.Currency;
import org.example.postlistingservice.services.ExchangeRatesService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final ExchangeRatesService exchangeRatesService;

    public Post mapToEntity(PostRequestDto dto) {
        return Post.builder()
                .carYear(dto.getCarYear())
                .carMake(dto.getCarMake())
                .carModel(dto.getCarModel())
                .sellingRegion(dto.getSellingRegion())
                .currency(dto.getCurrency())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .views(0)
                .build();
    }

    public PostResponseDto mapToDto(Post post) {
        String userEmail = post.getUserEmail();
        double priceInUah = post.getPrice();
        Currency currency = post.getCurrency();

        if (currency == Currency.USD) {
            priceInUah *= exchangeRatesService.getUahToUsdRate();
        } else if (currency == Currency.EUR) {
            priceInUah *= exchangeRatesService.getUahToEurRate();
        }

        return PostResponseDto.builder()
                .postId(post.getPostId())
                .userEmail(userEmail)
                .carYear(post.getCarYear())
                .carMake(post.getCarMake())
                .carModel(post.getCarModel())
                .sellingRegion(post.getSellingRegion())
                .price(priceInUah)
                .description(post.getDescription())
                .build();
    }

    public void updateEntityFromDto(PostRequestDto dto, Post post) {
        post.setCarYear(dto.getCarYear());
        post.setCarMake(dto.getCarMake());
        post.setCarModel(dto.getCarModel());
        post.setSellingRegion(dto.getSellingRegion());
        post.setCurrency(dto.getCurrency());
        post.setPrice(dto.getPrice());
        post.setDescription(dto.getDescription());
    }
}

