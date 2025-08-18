package com.microgram.dto;

import java.util.List;

public class UserListResponse {
    private List<UserSummary> users;
    private int count;

    public UserListResponse() {}

    public UserListResponse(List<UserSummary> users) {
        this.users = users;
        this.count = users.size();
    }

    public List<UserSummary> getUsers() { return users; }
    public void setUsers(List<UserSummary> users) { this.users = users; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public static class UserSummary {
        private Long id;
        private String username;
        private String displayName;
        private String profilePictureUrl;

        public UserSummary() {}

        public UserSummary(Long id, String username, String displayName, String profilePictureUrl) {
            this.id = id;
            this.username = username;
            this.displayName = displayName;
            this.profilePictureUrl = profilePictureUrl;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }

        public String getProfilePictureUrl() { return profilePictureUrl; }
        public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
    }
}