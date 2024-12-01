package org.example.postlistingservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.postlistingservice.DTO.AveragePriceResponseDto;
import org.example.postlistingservice.DTO.PostRequestDto;
import org.example.postlistingservice.DTO.PostResponseDto;
import org.example.postlistingservice.enums.SellingRegion;
import org.example.postlistingservice.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/posts")
@AllArgsConstructor

public class PostsController {
    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return new ResponseEntity<>(this.postService.getPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PostResponseDto>> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(Optional.of(this.postService.getPostById(id)), HttpStatus.OK);
    }

    @GetMapping("/avg-price/{region}")
    public ResponseEntity<AveragePriceResponseDto> getAveragePriceByRegion(@PathVariable SellingRegion region) {
        Double averagePrice = postService.getAveragePriceByRegion(region);
        return averagePrice != null ?
                new ResponseEntity<>(AveragePriceResponseDto
                        .builder()
                        .sellingRegion(region)
                        .averagePrice(averagePrice)
                        .build(),
                        HttpStatus.OK)
                :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createPost(@RequestBody PostRequestDto postRequestDto,
                                           HttpServletRequest request) {
        this.postService.requestAPost(postRequestDto, request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id,
                                           @RequestBody PostRequestDto postRequestDto,
                                           HttpServletRequest request) {
        postService.updatePost(id, postRequestDto, request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           HttpServletRequest request) {
        this.postService.deletePost(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
