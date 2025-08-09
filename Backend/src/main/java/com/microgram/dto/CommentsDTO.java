package com.microgram.dto;

import java.time.LocalDateTime;

public class CommentsDTO {
    private Long id;
    private Long userId;
    private String commentContent;
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