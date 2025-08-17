package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Like data transfer object")
public class LikesDTO {
    @Schema(description = "Like ID", example = "456")
    private Long id;
    
    @Schema(description = "User ID who liked the post", example = "89")
    private Long userId;
    
    @Schema(description = "Post ID that was liked", example = "123")
    private Long postId;
    
    @Schema(description = "Like creation timestamp", example = "2024-01-15T20:15:00")
    private LocalDateTime createdAt;

    public LikesDTO() {
    }

    public LikesDTO(Long id, Long userId, Long postId, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}