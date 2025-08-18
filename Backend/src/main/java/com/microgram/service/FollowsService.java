package com.microgram.service;

import com.microgram.dto.FollowStatusResponse;
import com.microgram.dto.UserListResponse;
import com.microgram.model.Follows;
import com.microgram.model.Users;
import com.microgram.repository.FollowsRepository;
import com.microgram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowsService {

    @Autowired
    private FollowsRepository followsRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public FollowStatusResponse followUser(String username, Long followingId) {
        Users follower = userRepository.findByUsername(username)
            .orElse(null);
        if (follower == null) {
            return new FollowStatusResponse(false, "User not found");
        }
        
        Long followerId = follower.getId();
        if (followerId.equals(followingId)) {
            return new FollowStatusResponse(false, "Cannot follow yourself");
        }

        if (!userRepository.existsById(followingId)) {
            return new FollowStatusResponse(false, "User not found");
        }

        if (followsRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            return new FollowStatusResponse(true, "Already following this user");
        }

        followsRepository.save(new Follows(followerId, followingId));
        return new FollowStatusResponse(true, "Successfully followed user");
    }

    @Transactional
    public FollowStatusResponse unfollowUser(String username, Long followingId) {
        Users follower = userRepository.findByUsername(username)
            .orElse(null);
        if (follower == null) {
            return new FollowStatusResponse(false, "User not found");
        }
        
        Long followerId = follower.getId();
        if (!followsRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            return new FollowStatusResponse(false, "Not following this user");
        }

        followsRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
        return new FollowStatusResponse(false, "Successfully unfollowed user");
    }

    public FollowStatusResponse checkFollowStatus(String username, Long followingId) {
        Users follower = userRepository.findByUsername(username)
            .orElse(null);
        if (follower == null) {
            return new FollowStatusResponse(false, "User not found");
        }
        
        Long followerId = follower.getId();
        boolean isFollowing = followsRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
        return new FollowStatusResponse(isFollowing, isFollowing ? "Following" : "Not following");
    }

    public UserListResponse getFollowers(Long userId) {
        List<Long> followerIds = followsRepository.findFollowerIdsByFollowingId(userId);
        List<Users> followers = userRepository.findAllById(followerIds);
        
        List<UserListResponse.UserSummary> userSummaries = followers.stream()
            .map(user -> new UserListResponse.UserSummary(
                user.getId(), user.getUsername(), user.getDisplayName(), user.getProfilePictureUrl()))
            .collect(Collectors.toList());
        
        return new UserListResponse(userSummaries);
    }

    public UserListResponse getFollowing(Long userId) {
        List<Long> followingIds = followsRepository.findFollowingIdsByFollowerId(userId);
        List<Users> following = userRepository.findAllById(followingIds);
        
        List<UserListResponse.UserSummary> userSummaries = following.stream()
            .map(user -> new UserListResponse.UserSummary(
                user.getId(), user.getUsername(), user.getDisplayName(), user.getProfilePictureUrl()))
            .collect(Collectors.toList());
        
        return new UserListResponse(userSummaries);
    }
}