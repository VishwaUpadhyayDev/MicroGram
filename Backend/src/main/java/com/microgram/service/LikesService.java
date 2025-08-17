package com.microgram.service;

import com.microgram.dto.LikesDTO;
import com.microgram.model.Likes;
import com.microgram.model.Users;
import com.microgram.repository.LikesRepository;
import com.microgram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikesService {

    @Autowired
    private LikesRepository likesRepository;
    

    
    @Autowired
    private UserRepository userRepository;

    public List<LikesDTO> getLikesByPostId(Long postId) {
        List<Likes> likes = likesRepository.findByPostId(postId);
        
        return likes.stream()
            .map(this::mapToLikesDTO)
            .collect(Collectors.toList());
    }
    
    private LikesDTO mapToLikesDTO(Likes like) {
        return new LikesDTO(
            like.getId(),
            like.getUserId(),
            like.getPostId(),
            like.getCreatedAt()
        );
    }
    
    public boolean addLike(Long postId, Long userId) {
        Likes like = new Likes();
        like.setPostId(postId);
        like.setUserId(userId);
        
        likesRepository.save(like);
        return true;
    }
    
    public boolean deleteLike(Long postId, Long userId) {
        Optional<Likes> like = likesRepository.findByPostIdAndUserId(postId, userId);
        
        if (like.isPresent()) {
            likesRepository.delete(like.get());
            return true;
        }
        
        return false;
    }
}