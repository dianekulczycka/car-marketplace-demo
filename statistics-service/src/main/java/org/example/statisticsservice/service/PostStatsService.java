package org.example.statisticsservice.service;

import lombok.RequiredArgsConstructor;
import org.example.statisticsservice.entities.PostStats;
import org.example.statisticsservice.repository.PostStatsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PostStatsService {
    private final PostStatsRepository postStatsRepository;

    public void updateViews(Integer postId) {
        if (postId == null) {
            throw new IllegalArgumentException("Post id is null");
        }

        PostStats postStats = this.postStatsRepository
                .findByPostId(postId)
                .orElseGet(() -> new PostStats(postId));

        postStats.setViews(postStats.getViews() + 1);
        this.postStatsRepository.save(postStats);
    }

    public Integer getViews(Integer postId) {
        return this.postStatsRepository
                .findByPostId(postId)
                .map(PostStats::getViews)
                .orElse(null);
    }
}
