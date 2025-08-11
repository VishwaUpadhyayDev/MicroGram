package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Login request payload")
public class LoginRequest {
    
    @Schema(description = "Username or email address", example = "sarah_photographer")
    @NotBlank(message = "Username/email cannot be blank")
    private String usernameOrEmail;
    
    @Schema(description = "User password", example = "MySecurePass2024!")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    // Getters and Setters
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}