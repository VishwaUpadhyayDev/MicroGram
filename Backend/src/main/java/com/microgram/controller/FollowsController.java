package com.microgram.controller;

import com.microgram.dto.FollowStatusResponse;
import com.microgram.dto.UserListResponse;
import com.microgram.service.FollowsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
@Tag(name = "Follows", description = "Follow/Unfollow operations")
public class FollowsController {

    @Autowired
    private FollowsService followsService;

    @PostMapping("/{userId}")
    @Operation(summary = "Follow a user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FollowStatusResponse> followUser(
            @PathVariable Long userId,
            Authentication authentication) {
        
        String username = authentication.getName();
        FollowStatusResponse response = followsService.followUser(username, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Unfollow a user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FollowStatusResponse> unfollowUser(
            @PathVariable Long userId,
            Authentication authentication) {
        
        String username = authentication.getName();
        FollowStatusResponse response = followsService.unfollowUser(username, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check/{userId}")
    @Operation(summary = "Check if following a user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FollowStatusResponse> checkFollowStatus(
            @PathVariable Long userId,
            Authentication authentication) {
        
        String username = authentication.getName();
        FollowStatusResponse response = followsService.checkFollowStatus(username, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/followers")
    @Operation(summary = "Get user's followers")
    public ResponseEntity<UserListResponse> getFollowers(@PathVariable Long userId) {
        UserListResponse response = followsService.getFollowers(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/following")
    @Operation(summary = "Get users that this user follows")
    public ResponseEntity<UserListResponse> getFollowing(@PathVariable Long userId) {
        UserListResponse response = followsService.getFollowing(userId);
        return ResponseEntity.ok(response);
    }
}