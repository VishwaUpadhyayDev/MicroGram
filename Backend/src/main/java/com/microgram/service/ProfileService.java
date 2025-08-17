package com.microgram.service;

import com.microgram.dto.ProfileResponse;
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

    public Optional<ProfileResponse> getMyProfile(String username) {
        Optional<Users> user = userRepository.findByUsername(username);
        
        if (user.isEmpty()) {
            return Optional.empty();
        }
        
        return Optional.of(mapToProfileResponse(user.get(), null));
    }

    public Optional<ProfileResponse> getUserProfile(String username, String currentUsername) {
        Optional<Users> user = userRepository.findByUsername(username);
        
        if (user.isEmpty()) {
            return Optional.empty();
        }
        
        Users u = user.get();
        
        // Allow access if profile is public or user is viewing their own profile
        if (!u.getIsPublic() && !username.equals(currentUsername)) {
            return Optional.empty();
        }
        
        return Optional.of(mapToProfileResponse(u, currentUsername));
    }

    public Optional<ProfileResponse> updateProfile(String username, Map<String, String> updates) {
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
        
        return Optional.of(mapToProfileResponse(user, null));
    }
    
    private ProfileResponse mapToProfileResponse(Users user, String currentUsername) {
        ProfileResponse response = new ProfileResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setProfilePictureUrl(user.getProfilePictureUrl());
        response.setIsPublic(user.getIsPublic());
        response.setPostsCount(user.getPostCount());
        response.setFollowersCount(user.getFollowerCount());
        response.setFollowingCount(user.getFollowingCount());
        return response;
    }
}