package org.example.postlistingservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.postlistingservice.enums.CarMake;
import org.example.postlistingservice.enums.Currency;
import org.example.postlistingservice.enums.SellingRegion;
import org.example.postlistingservice.enums.Status;
import org.springframework.stereotype.Repository;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "posts")

@Repository
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "car_year")
    private Integer carYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_make")
    private CarMake carMake;

    @Column(name = "car_model")
    private String carModel;

    @Enumerated(EnumType.STRING)
    @Column(name = "selling_region")
    private SellingRegion sellingRegion;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Double price;
    private String description;
    private Integer views = 0;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "user_email")
    private String userEmail;
}
