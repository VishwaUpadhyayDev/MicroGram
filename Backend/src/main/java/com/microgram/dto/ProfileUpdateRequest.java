package com.microgram.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Profile update request")
public class ProfileUpdateRequest {

    @Schema(description = "Display name", example = "John Doe")
    private String displayName;

    @Schema(description = "User bio", example = "Software Developer passionate about technology")
    private String bio;

    @Schema(description = "Profile picture URL", example = "https://example.com/profile.jpg")
    private String profilePictureUrl;

    @Schema(description = "Profile visibility", example = "true")
    private String isPublic;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }
}