package com.microgram.controller;

import com.microgram.dto.ProfileUpdateRequest;
import com.microgram.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        Optional<Map<String, Object>> profile = profileService.getMyProfile(username);
        
        return profile.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserProfile(@PathVariable String username, Authentication authentication) {
        String currentUsername = authentication != null ? authentication.getName() : null;
        Optional<Map<String, Object>> profile = profileService.getUserProfile(username, currentUsername);
        
        return profile.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateProfile(Authentication authentication, @RequestBody ProfileUpdateRequest request) {
        String username = authentication.getName();
        
        Map<String, String> updates = new HashMap<>();
        if (request.getDisplayName() != null) updates.put("displayName", request.getDisplayName());
        if (request.getBio() != null) updates.put("bio", request.getBio());
        if (request.getProfilePictureUrl() != null) updates.put("profilePictureUrl", request.getProfilePictureUrl());
        if (request.getIsPublic() != null) updates.put("isPublic", request.getIsPublic());
        
        Optional<Map<String, Object>> profile = profileService.updateProfile(username, updates);
        
        return profile.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
}