package com.microgram.controller;

import com.microgram.dto.LikesDTO;
import com.microgram.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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

@RestController
@RequestMapping("/api/likes")
@Tag(name = "Likes", description = "Like management APIs for post engagement")
public class LikesController {

    @Autowired
    private LikesService likesService;

    @Operation(summary = "Get likes for post", 
              description = "Retrieve all likes for a specific post",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Likes retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = LikesDTO.class))),
        @ApiResponse(responseCode = "404", description = "No likes found for this post"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/post/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getLikesByPostId(
            @Parameter(description = "Post ID to get likes for", example = "123") 
            @PathVariable Long postId) {
        try {
            List<LikesDTO> likes = likesService.getLikesByPostId(postId);
            if (likes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(likes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
    @Operation(summary = "Like a post", 
              description = "Add a like to a specific post",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Like added successfully"),
        @ApiResponse(responseCode = "500", description = "Failed to add like")
    })
    @PostMapping("/post/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addLike(
            @Parameter(description = "Post ID to like", example = "123") 
            @PathVariable Long postId, 
            @Parameter(description = "User ID who is liking the post", example = "89") 
            @RequestParam Long userId) {
        try {
            boolean success = likesService.addLike(postId, userId);
            
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Like added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add like");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
    @Operation(summary = "Unlike a post", 
              description = "Remove a like from a specific post",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Like removed successfully"),
        @ApiResponse(responseCode = "404", description = "Like not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/post/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteLike(
            @Parameter(description = "Post ID to unlike", example = "123") 
            @PathVariable Long postId, 
            @Parameter(description = "User ID who is unliking the post", example = "89") 
            @RequestParam Long userId) {
        try {
            boolean success = likesService.deleteLike(postId, userId);
            
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