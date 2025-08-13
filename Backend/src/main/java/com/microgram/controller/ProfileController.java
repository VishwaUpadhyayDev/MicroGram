package com.microgram.controller;

import com.microgram.dto.ProfileUpdateRequest;
import com.microgram.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Profile", description = "User profile management APIs")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Operation(summary = "Get current user profile", 
              description = "Retrieve the authenticated user's own profile information",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profile retrieved successfully",
                    content = @Content(
                        examples = @ExampleObject(
                            name = "User Profile Example",
                            value = "{\"id\": 45, \"username\": \"sarah_photographer\", \"displayName\": \"Sarah Mitchell\", \"email\": \"sarah.mitchell@gmail.com\", \"bio\": \"📸 Travel photographer | 🌍 Exploring the world one shot at a time | 📍 Currently in Bali\", \"profilePictureUrl\": \"https://microgram-profiles.s3.amazonaws.com/users/sarah_photographer_avatar.jpg\", \"isPublic\": true, \"postsCount\": 127, \"followersCount\": 2543, \"followingCount\": 891}"
                        )
                    )),
        @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        Optional<Map<String, Object>> profile = profileService.getMyProfile(username);
        
        return profile.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user profile by username", 
              description = "Retrieve another user's profile information",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profile retrieved successfully",
                    content = @Content(
                        examples = @ExampleObject(
                            name = "Other User Profile Example",
                            value = "{\"id\": 67, \"username\": \"alex_traveler\", \"displayName\": \"Alex Rodriguez\", \"bio\": \"✈️ Digital nomad | 🏔️ Adventure seeker | 📱 Tech enthusiast\", \"profilePictureUrl\": \"https://microgram-profiles.s3.amazonaws.com/users/alex_traveler_avatar.jpg\", \"isPublic\": true, \"postsCount\": 89, \"followersCount\": 1205, \"followingCount\": 456, \"isFollowing\": false}"
                        )
                    )),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{username}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserProfile(
            @Parameter(description = "Username to get profile for", example = "alex_traveler") 
            @PathVariable String username, Authentication authentication) {
        String currentUsername = authentication != null ? authentication.getName() : null;
        Optional<Map<String, Object>> profile = profileService.getUserProfile(username, currentUsername);
        
        return profile.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update current user profile", 
              description = "Update the authenticated user's profile information",
              security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                    content = @Content(
                        examples = @ExampleObject(
                            name = "Updated Profile Example",
                            value = "{\"id\": 45, \"username\": \"sarah_photographer\", \"displayName\": \"Sarah Mitchell\", \"email\": \"sarah.mitchell@gmail.com\", \"bio\": \"📸 Travel photographer | 🌍 Exploring the world one shot at a time | 📍 Currently in Bali\", \"profilePictureUrl\": \"https://microgram-profiles.s3.amazonaws.com/users/sarah_photographer_avatar.jpg\", \"isPublic\": true, \"postsCount\": 127, \"followersCount\": 2543, \"followingCount\": 891}"
                        )
                    )),
        @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateProfile(Authentication authentication, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Profile update request",
                content = @Content(schema = @Schema(implementation = ProfileUpdateRequest.class))
            )
            @RequestBody ProfileUpdateRequest request) {
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