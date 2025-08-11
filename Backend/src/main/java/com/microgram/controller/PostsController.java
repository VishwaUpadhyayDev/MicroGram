package com.microgram.controller;

import com.microgram.dto.PostsDTO;
import com.microgram.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getPostsByUserId(
            @Parameter(description = "User ID to get posts for", example = "45") 
            @PathVariable Long userId) {
        try {
            List<PostsDTO> posts = postsService.getPostsByUserId(userId);
            if (posts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
    @Operation(summary = "Create new post", 
              description = "Create a new social media post with content and optional image",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Post created successfully"),
        @ApiResponse(responseCode = "500", description = "Failed to create post")
    })
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addPost(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Post creation request",
                content = @Content(
                    examples = @ExampleObject(
                        name = "Travel Post Example",
                        value = "{\"userId\": \"45\", \"content\": \"Amazing day exploring the mountains! The view from the top was absolutely breathtaking 🏔️ #hiking #nature #adventure\", \"imageUrl\": \"https://microgram-images.s3.amazonaws.com/posts/mountain-view-2024.jpg\"}"
                    )
                )
            )
            @RequestBody Map<String, String> request) {
        try {
            Long userId = Long.valueOf(request.get("userId"));
            String content = request.get("content");
            String imageUrl = request.get("imageUrl");
            
            boolean success = postsService.addPost(userId, content, imageUrl);
            
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Post added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add post");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
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
    public ResponseEntity<?> deletePost(
            @Parameter(description = "Post ID to delete", example = "123") 
            @PathVariable Long postId, 
            @Parameter(description = "User ID (must be post owner)", example = "45") 
            @RequestParam Long userId) {
        try {
            boolean success = postsService.deletePost(postId, userId);
            
            if (success) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}