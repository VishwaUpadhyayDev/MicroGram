package com.microgram.service;

import com.microgram.dto.PostsDTO;
import com.microgram.model.Posts;
import com.microgram.model.Users;
import com.microgram.repository.PostRepository;
import com.microgram.repository.UserRepository;
import com.microgram.repository.LikesRepository;
import com.microgram.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostsService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LikesRepository likesRepository;
    
    @Autowired
    private CommentsRepository commentsRepository;

    public List<PostsDTO> getPostsByUserId(Long userId) {
        List<Posts> posts = postRepository.findByUserId(userId);
        
        return posts.stream()
            .map(post -> new PostsDTO(
                post.getId(),
                post.getUserId(),
                null,
                post.getContent(),
                post.getImageUrl(),
                post.getLikesCount(),
                post.getCreatedAt()
            ))
            .collect(Collectors.toList());
    }
    
    public boolean addPost(Long userId, String content, String imageUrl) {
        Posts post = new Posts();
        post.setUserId(userId);
        post.setContent(content);
        post.setImageUrl(imageUrl);
        
        postRepository.save(post);
        return true;
    }
    
    public boolean deletePost(Long postId, Long userId) {
        Optional<Posts> post = postRepository.findById(postId);
        
        if (post.isPresent() && post.get().getUserId().equals(userId)) {
            likesRepository.deleteByPostId(postId);
            commentsRepository.deleteByPostId(postId);
            postRepository.delete(post.get());
            return true;
        }
        
        return false;
    }
}