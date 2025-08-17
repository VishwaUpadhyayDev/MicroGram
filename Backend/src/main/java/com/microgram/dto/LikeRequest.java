package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to like/unlike a post")
public class LikeRequest {
    
    @Schema(description = "User ID performing the like action", example = "89")
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}