package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to create a new post")
public class CreatePostRequest {
    
    @Schema(description = "User ID creating the post", example = "45")
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    
    @Schema(description = "Post content/caption", example = "Amazing day exploring the mountains! The view from the top was absolutely breathtaking 🏔️ #hiking #nature #adventure")
    @NotBlank(message = "Content cannot be blank")
    private String content;
    
    @Schema(description = "Optional image URL", example = "https://microgram-images.s3.amazonaws.com/posts/mountain-view-2024.jpg")
    private String imageUrl;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}