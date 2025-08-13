package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Comment data transfer object")
public class CommentsDTO {
    @Schema(description = "Comment ID", example = "789")
    private Long id;
    
    @Schema(description = "User ID who made the comment", example = "67")
    private Long userId;
    
    @Schema(description = "Comment text content", example = "Wow, this is absolutely stunning! 😍 Where was this taken?")
    private String commentContent;
    
    @Schema(description = "Comment creation timestamp", example = "2024-01-15T19:45:00")
    private LocalDateTime createdAt;

    public CommentsDTO() {
    }

    public CommentsDTO(Long id, Long userId, String commentContent, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}