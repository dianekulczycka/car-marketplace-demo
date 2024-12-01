package org.example.postlistingservice.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postlistingservice.DTO.*;
import org.example.postlistingservice.entities.Post;
import org.example.postlistingservice.enums.Role;
import org.example.postlistingservice.enums.SellingRegion;
import org.example.postlistingservice.enums.Status;
import org.example.postlistingservice.mappers.PostMapper;
import org.example.postlistingservice.repositories.PostRepository;
import org.example.postlistingservice.utilities.JwtUtility;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final JwtUtility jwtUtility;
    private final PostEventsProducer postEventsProducer;
    private final PostCreatedEventProducer postCreatedEventProducer;
    private final PostCheckRequestEventProducer postCheckRequestEventProducer;
    private final PendingPostCreationEventProducer pendingPostCreationEventProducer;

    public List<PostResponseDto> getPosts() {
        return this.postRepository.findAll()
                .stream()
                .filter(post -> post.getStatus() == Status.ACCEPTED)
                .map(postMapper::mapToDto)
                .toList();
    }

    @Transactional
    public PostResponseDto getPostById(Long id) {
        Post post = this.postRepository
                .findById(id)
                .filter(p -> p.getStatus() == Status.ACCEPTED)
                .orElseThrow(() -> new IllegalArgumentException("post not found!"));

        post.setViews(post.getViews() + 1);
        postRepository.save(post);

        postEventsProducer.sendEvent(
                PostStatsUpdatedEvent.builder()
                        .postId(id)
                        .build()
        );

        return this.postMapper.mapToDto(post);
    }

    public Double getAveragePriceByRegion(SellingRegion sellingRegion) {
        return this.postRepository.findAveragePriceBySellingRegion(sellingRegion);
    }

    @Transactional
    public void requestAPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtility.extractTokenFromRequest(request);

        if (this.jwtUtility.isTokenExpired(token)) {
            throw new IllegalArgumentException("Token expired");
        }

        String email = this.jwtUtility.extractUsernameFromToken(token);

        Post post = this.postMapper.mapToEntity(postRequestDto);
        post.setUserEmail(email);
        post.setStatus(Status.PENDING);
        this.postRepository.save(post);

        PostCheckRequestEvent requestEvent = new PostCheckRequestEvent(email);
        this.postCheckRequestEventProducer.sendEvent(requestEvent);

        PendingPostCreationEvent pendingPostEvent = new PendingPostCreationEvent(post.getPostId());
        this.pendingPostCreationEventProducer.sendEvent(pendingPostEvent);

        log.info("user requested a post: {}", email);
    }

    @Transactional
    public void publishAPost(String userEmail, boolean canMakePosts) {
        List<Post> posts = this.postRepository
                .findPostsByUserEmail(userEmail)
                .orElse(Collections.emptyList());

        if (posts.isEmpty()) {
            log.error("user has no posts");
            return;
        }

        Post pendingPost = posts.stream()
                .filter(post -> post.getStatus() == Status.PENDING)
                .findFirst()
                .orElse(null);

        if (pendingPost == null) {
            log.error("no post pending");
            return;
        }

        if (canMakePosts) {
            pendingPost.setStatus(Status.ACCEPTED);
            log.info("post has been published!");
        } else {
            pendingPost.setStatus(Status.REJECTED);
            log.info("failed to publish a post. please upgrade to premium");
        }

        this.postRepository.save(pendingPost);

        if (canMakePosts) {
            PostCreatedEvent event = new PostCreatedEvent(userEmail);
            this.postCreatedEventProducer.sendEvent(event);
            log.info("UserPostCreatedEvent sent");
        }
    }

    @Transactional
    public void updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtility.extractTokenFromRequest(request);

        if (this.jwtUtility.isTokenExpired(token)) {
            throw new IllegalArgumentException("Token expired");
        }

        String email = this.jwtUtility.extractUsernameFromToken(token);
        String role = this.jwtUtility.extractRoleFromToken(token);

        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not exist"));

        if (Role.ROLE_MANAGER.toString().equals(role) || Role.ROLE_ADMIN.toString().equals(role)) {
            this.postMapper.updateEntityFromDto(postRequestDto, post);
            this.postRepository.save(post);
            return;
        }

        if (!post.getUserEmail().equals(email)) {
            throw new IllegalArgumentException("you can only update your posts!");
        }

        this.postMapper.updateEntityFromDto(postRequestDto, post);
        this.postRepository.save(post);
    }

    public void deletePost(Long id, HttpServletRequest request) {
        String token = jwtUtility.extractTokenFromRequest(request);
        if (this.jwtUtility.isTokenExpired(token)) {
            throw new IllegalArgumentException("token expired");
        }

        String email = this.jwtUtility.extractUsernameFromToken(token);
        String role = this.jwtUtility.extractRoleFromToken(token);

        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("post not exist"));

        if (Role.ROLE_MANAGER.toString().equals(role) || Role.ROLE_ADMIN.toString().equals(role)) {
            this.postRepository.deleteById(id);
            return;
        }

        if (!post.getUserEmail().equals(email)) {
            throw new IllegalArgumentException("you can only delete your posts!");
        }

        this.postRepository.deleteById(id);
    }

}
