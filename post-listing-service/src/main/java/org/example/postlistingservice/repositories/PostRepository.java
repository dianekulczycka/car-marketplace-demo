package org.example.postlistingservice.repositories;

import org.example.postlistingservice.entities.Post;
import org.example.postlistingservice.enums.SellingRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findPostsByUserEmail(String email);

    @Query("SELECT AVG(p.price) FROM Post p WHERE p.sellingRegion = :sellingRegion AND p.status = 'ACCEPTED'")
    Double findAveragePriceBySellingRegion(SellingRegion sellingRegion);
}
