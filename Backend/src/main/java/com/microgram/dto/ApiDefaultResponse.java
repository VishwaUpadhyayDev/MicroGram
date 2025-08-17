package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard API response")
public class ApiDefaultResponse {
    
    @Schema(description = "Response message", example = "Post created successfully")
    private String message;
    
    @Schema(description = "Success status", example = "true")
    private boolean success;

    public ApiDefaultResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}