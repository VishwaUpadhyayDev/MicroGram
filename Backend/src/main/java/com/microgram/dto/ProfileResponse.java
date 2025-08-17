package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User profile response")
public class ProfileResponse {
    
    @Schema(description = "User ID", example = "45")
    private Long id;
    
    @Schema(description = "Username", example = "sarah_photographer")
    private String username;

    @Schema(description = "Email address", example = "sarah.mitchell@gmail.com")
    private String email;

    @Schema(description = "Profile picture URL", example = "https://microgram-profiles.s3.amazonaws.com/users/sarah_photographer_avatar.jpg")
    private String profilePictureUrl;
    
    @Schema(description = "Profile visibility", example = "true")
    private Boolean isPublic;
    
    @Schema(description = "Number of posts", example = "127")
    private Integer postsCount;
    
    @Schema(description = "Number of followers", example = "2543")
    private Integer followersCount;
    
    @Schema(description = "Number of following", example = "891")
    private Integer followingCount;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
    
    public Boolean getIsPublic() { return isPublic; }
    public void setIsPublic(Boolean isPublic) { this.isPublic = isPublic; }
    
    public Integer getPostsCount() { return postsCount; }
    public void setPostsCount(Integer postsCount) { this.postsCount = postsCount; }
    
    public Integer getFollowersCount() { return followersCount; }
    public void setFollowersCount(Integer followersCount) { this.followersCount = followersCount; }
    
    public Integer getFollowingCount() { return followingCount; }
    public void setFollowingCount(Integer followingCount) { this.followingCount = followingCount; }
}