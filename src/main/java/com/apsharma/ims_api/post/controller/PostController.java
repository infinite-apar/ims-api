package com.apsharma.ims_api.post.controller;

import com.apsharma.ims_api.post.dto.PostRequest;
import com.apsharma.ims_api.post.dto.PostResponse;
import com.apsharma.ims_api.post.dto.PostStatusUpdateRequest;
import com.apsharma.ims_api.post.model.Post;
import com.apsharma.ims_api.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.apsharma.ims_api.util.ApiResponseBuilder;

import java.util.List;
import java.util.Map;




@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody @Valid PostRequest request) {

        PostResponse response = postService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseBuilder()
                        .status(HttpStatus.CREATED)
                        .message("Post created successfully")
                        .data(response)
                        .build()
                    );
    }

//    @GetMapping
//    public ResponseEntity<List<PostResponse>> getAllPosts() {
//        return ResponseEntity.ok(postService.list());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPostById(@PathVariable Long id) {
        PostResponse postResponse = postService.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Post fetched successfully")
                        .data(postResponse)
                        .build()
                );

    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable Long id, @RequestBody @Valid PostRequest request) {
        PostResponse postResponse = postService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Post updated successfully")
                        .data(postResponse)
                        .build()
                );

    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(@PathVariable Long id, @RequestBody @Valid PostStatusUpdateRequest request) {
        PostResponse postResponse = postService.updateStatus(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Post status updated successfully")
                        .data(postResponse)
                        .build()
                );
    }
}