package com.microgram.service;

import com.microgram.model.Users;
import com.microgram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    public Optional<Map<String, Object>> getMyProfile(String username) {
        Optional<Users> user = userRepository.findByUsername(username);
        
        if (user.isEmpty()) {
            return Optional.empty();
        }
        
        Users u = user.get();
        return Optional.of(Map.of(
            "username", u.getUsername(),
            "displayName", u.getDisplayName() != null ? u.getDisplayName() : "",
            "bio", u.getBio() != null ? u.getBio() : "",
            "profilePictureUrl", u.getProfilePictureUrl() != null ? u.getProfilePictureUrl() : "",
            "followerCount", u.getFollowerCount(),
            "followingCount", u.getFollowingCount(),
            "postCount", u.getPostCount(),
            "isPublic", u.getIsPublic()
        ));
    }

    public Optional<Map<String, Object>> getUserProfile(String username, String currentUsername) {
        Optional<Users> user = userRepository.findByUsername(username);
        
        if (user.isEmpty()) {
            return Optional.empty();
        }
        
        Users u = user.get();
        
        // Allow access if profile is public or user is viewing their own profile
        if (!u.getIsPublic() && !username.equals(currentUsername)) {
            return Optional.empty();
        }
        
        return Optional.of(Map.of(
            "username", u.getUsername(),
            "displayName", u.getDisplayName() != null ? u.getDisplayName() : "",
            "bio", u.getBio() != null ? u.getBio() : "",
            "profilePictureUrl", u.getProfilePictureUrl() != null ? u.getProfilePictureUrl() : "",
            "followerCount", u.getFollowerCount(),
            "followingCount", u.getFollowingCount(),
            "postCount", u.getPostCount(),
            "isPublic", u.getIsPublic()
        ));
    }

    public Optional<Map<String, Object>> updateProfile(String username, Map<String, String> updates) {
        Optional<Users> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Users user = userOpt.get();
        
        if (updates.containsKey("displayName")) {
            user.setDisplayName(updates.get("displayName"));
        }
        if (updates.containsKey("bio")) {
            user.setBio(updates.get("bio"));
        }
        if (updates.containsKey("profilePictureUrl")) {
            user.setProfilePictureUrl(updates.get("profilePictureUrl"));
        }
        if (updates.containsKey("isPublic")) {
            user.setIsPublic(Boolean.parseBoolean(updates.get("isPublic")));
        }
        
        userRepository.save(user);
        
        return Optional.of(Map.of(
            "username", user.getUsername(),
            "displayName", user.getDisplayName() != null ? user.getDisplayName() : "",
            "bio", user.getBio() != null ? user.getBio() : "",
            "profilePictureUrl", user.getProfilePictureUrl() != null ? user.getProfilePictureUrl() : "",
            "followerCount", user.getFollowerCount(),
            "followingCount", user.getFollowingCount(),
            "postCount", user.getPostCount(),
            "isPublic", user.getIsPublic()
        ));
    }
}