package org.example.statisticsservice.repository;

import org.example.statisticsservice.entities.PostStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostStatsRepository extends JpaRepository<PostStats, Integer> {
    Optional<PostStats> findByPostId(Integer postId);
}