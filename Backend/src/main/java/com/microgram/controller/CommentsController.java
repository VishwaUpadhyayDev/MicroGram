package com.microgram.controller;

import com.microgram.dto.CommentsDTO;
import com.microgram.service.CommentsService;
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
@RequestMapping("/api/comments")
@Tag(name = "Comments", description = "Comment management APIs for post interactions")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @Operation(summary = "Get comments for post", 
              description = "Retrieve all comments for a specific post",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comments retrieved successfully", 
                    content = @Content(schema = @Schema(implementation = CommentsDTO.class))),
        @ApiResponse(responseCode = "404", description = "No comments found for this post"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/post/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCommentsByPostId(
            @Parameter(description = "Post ID to get comments for", example = "123") 
            @PathVariable Long postId) {
        try {
            List<CommentsDTO> comments = commentsService.getCommentsByPostId(postId);
            if (comments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
    @Operation(summary = "Add comment to post", 
              description = "Add a new comment to a specific post",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comment added successfully"),
        @ApiResponse(responseCode = "400", description = "Failed to add comment"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/post/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addComment(
            @Parameter(description = "Post ID to comment on", example = "123") 
            @PathVariable Long postId, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Comment creation request",
                content = @Content(
                    examples = @ExampleObject(
                        name = "Comment Example",
                        value = "{\"userId\": \"67\", \"commentContent\": \"This photo is incredible! The lighting is perfect 📸 What camera did you use?\"}"
                    )
                )
            )
            @RequestBody Map<String, String> request) {
        try {
            Long userId = Long.valueOf(request.get("userId"));
            String commentContent = request.get("commentContent");
            
            boolean success = commentsService.addComment(postId, userId, commentContent);
            
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Comment added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add comment");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}