package com.microgram.service;

import com.microgram.dto.CreatePostRequest;
import com.microgram.dto.PostsDTO;
import com.microgram.model.Posts;
import com.microgram.model.Users;
import com.microgram.repository.PostRepository;
import com.microgram.repository.UserRepository;
import com.microgram.repository.LikesRepository;
import com.microgram.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    
    @Autowired
    private StorageService storageService;

    public List<PostsDTO> getPostsByUserId(Long userId) {
        List<Posts> posts = postRepository.findByUserId(userId);
        Optional<Users> user = userRepository.findById(userId);
        String username = user.map(Users::getUsername).orElse(null);
        
        return posts.stream()
            .map(post -> mapToPostsDTO(post, username))
            .collect(Collectors.toList());
    }
    
    private PostsDTO mapToPostsDTO(Posts post, String username) {
        return new PostsDTO(
            post.getId(),
            post.getUserId(),
            username,
            post.getContent(),
            post.getImageUrl(),
            post.getLikesCount(),
            post.getCreatedAt()
        );
    }
    
    public boolean addPost(CreatePostRequest request) throws IOException {
        Posts post = new Posts();
        post.setUserId(request.getUserId());
        post.setContent(request.getContent());
        
        String imageUrl = null;
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            imageUrl = storageService.uploadImage(request.getImage());
        }
        
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