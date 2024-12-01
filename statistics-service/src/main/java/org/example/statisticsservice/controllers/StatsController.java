package org.example.statisticsservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.statisticsservice.DTO.PostStatsDto;
import org.example.statisticsservice.service.PostStatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/stats")
@RequiredArgsConstructor

public class StatsController {
    private final PostStatsService postStatsService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostStatsDto> getPostStats(@PathVariable Integer postId) {
        Integer totalViews = postStatsService.getViews(postId);
        return new ResponseEntity<>(PostStatsDto
                .builder()
                .postId(postId)
                .totalViews(totalViews)
                .build(), HttpStatus.OK);
    }
}
