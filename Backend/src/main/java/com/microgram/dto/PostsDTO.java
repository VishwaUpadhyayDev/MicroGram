package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Post data transfer object")
public class PostsDTO {
    @Schema(description = "Post ID", example = "123")
    private Long id;
    
    @Schema(description = "User ID who created the post", example = "45")
    private Long userId;
    
    @Schema(description = "Username of post creator", example = "sarah_photographer")
    private String username;
    
    @Schema(description = "Post content/caption", example = "Just captured this amazing sunset at the beach! 🌅 #photography #sunset")
    private String content;
    
    @Schema(description = "Image URL", example = "https://microgram-images.s3.amazonaws.com/posts/sunset-beach-2024.jpg")
    private String imageUrl;
    
    @Schema(description = "Number of likes", example = "127")
    private Integer likesCount;
    
    @Schema(description = "Post creation timestamp", example = "2024-01-15T18:30:00")
    private LocalDateTime createdAt;

    public PostsDTO() {
    }

    public PostsDTO(Long id, Long userId, String username, String content, String imageUrl, Integer likesCount, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.imageUrl = imageUrl;
        this.likesCount = likesCount;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}