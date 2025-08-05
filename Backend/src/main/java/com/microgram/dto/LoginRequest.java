package com.microgram.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    
    @NotBlank(message = "Username/email cannot be blank")
    private String usernameOrEmail;
    
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