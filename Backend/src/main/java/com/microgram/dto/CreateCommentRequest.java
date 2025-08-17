package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to create a new comment")
public class CreateCommentRequest {
    
    @Schema(description = "User ID creating the comment", example = "67")
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    
    @Schema(description = "Comment text content", example = "This photo is incredible! The lighting is perfect 📸 What camera did you use?")
    @NotBlank(message = "Comment content cannot be blank")
    private String commentContent;

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
}