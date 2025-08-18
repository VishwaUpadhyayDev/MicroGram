package com.microgram.dto;

public class FollowStatusResponse {
    private boolean isFollowing;
    private String message;

    public FollowStatusResponse() {}

    public FollowStatusResponse(boolean isFollowing, String message) {
        this.isFollowing = isFollowing;
        this.message = message;
    }

    public boolean isFollowing() { return isFollowing; }
    public void setFollowing(boolean following) { isFollowing = following; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}