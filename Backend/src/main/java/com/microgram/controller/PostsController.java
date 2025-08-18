package com.microgram.controller;

import com.microgram.dto.ApiDefaultResponse;
import com.microgram.dto.CreatePostRequest;
import com.microgram.dto.PostsDTO;
import com.microgram.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Post management APIs for social media content")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @Operation(summary = "Get posts by user", 
              description = "Retrieve all posts created by a specific user",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Posts retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PostsDTO.class))),
        @ApiResponse(responseCode = "404", description = "No posts found for this user"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PostsDTO>> getPostsByUserId(
            @Parameter(description = "User ID to get posts for", example = "45") 
            @PathVariable Long userId) {
        List<PostsDTO> posts = postsService.getPostsByUserId(userId);
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(posts);
    }
    
    @Operation(summary = "Create new post", 
              description = "Create a new social media post with content and optional image",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Post created successfully"),
        @ApiResponse(responseCode = "500", description = "Failed to create post")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDefaultResponse> addPost(@Valid @ModelAttribute CreatePostRequest request) {
        try {
            boolean success = postsService.addPost(request);
            
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ApiDefaultResponse("Post created successfully", true));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiDefaultResponse("Failed to create post", false));
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiDefaultResponse("Failed to upload image: " + e.getMessage(), false));
        }
    }
    
    @Operation(summary = "Delete post", 
              description = "Delete a specific post (only by the post owner)",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Post not found or not authorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDefaultResponse> deletePost(
            @Parameter(description = "Post ID to delete", example = "123") 
            @PathVariable Long postId, 
            @Parameter(description = "User ID (must be post owner)", example = "45") 
            @RequestParam Long userId) {
        boolean success = postsService.deletePost(postId, userId);
        
        if (success) {
            return ResponseEntity.ok(new ApiDefaultResponse("Post deleted successfully", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiDefaultResponse("Post not found or unauthorized", false));
        }
    }
}