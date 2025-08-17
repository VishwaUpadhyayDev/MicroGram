package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "Request to create a new post")
public class CreatePostRequest {
    
    @Schema(description = "User ID creating the post", example = "45")
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    
    @Schema(description = "Post content/caption", example = "Amazing day exploring the mountains! The view from the top was absolutely breathtaking 🏔️ #hiking #nature #adventure")
    @NotBlank(message = "Content cannot be blank")
    private String content;
    
    @Schema(description = "Optional image file", type = "string", format = "binary")
    private MultipartFile image;

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}