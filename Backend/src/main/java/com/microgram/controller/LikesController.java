package com.microgram.controller;

import com.microgram.dto.LikesDTO;
import com.microgram.dto.LikeRequest;
import com.microgram.dto.ApiDefaultResponse;
import com.microgram.service.LikesService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<LikesDTO>> getLikesByPostId(
            @Parameter(description = "Post ID to get likes for", example = "123") 
            @PathVariable Long postId) {
        List<LikesDTO> likes = likesService.getLikesByPostId(postId);
        if (likes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(likes);
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
    public ResponseEntity<ApiDefaultResponse> addLike(
            @Parameter(description = "Post ID to like", example = "123") 
            @PathVariable Long postId, 
            @Valid @RequestBody LikeRequest request) {
        boolean success = likesService.addLike(postId, request.getUserId());
        
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiDefaultResponse("Like added successfully", true));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiDefaultResponse("Failed to add like", false));
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
    public ResponseEntity<ApiDefaultResponse> deleteLike(
            @Parameter(description = "Post ID to unlike", example = "123") 
            @PathVariable Long postId, 
            @Valid @RequestBody LikeRequest request) {
        boolean success = likesService.deleteLike(postId, request.getUserId());
        
        if (success) {
            return ResponseEntity.ok(new ApiDefaultResponse("Like removed successfully", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiDefaultResponse("Like not found", false));
        }
    }
}