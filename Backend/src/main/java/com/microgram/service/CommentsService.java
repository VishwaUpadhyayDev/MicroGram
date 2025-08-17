package com.microgram.service;

import com.microgram.dto.CommentsDTO;
import com.microgram.model.Comments;
import com.microgram.model.Posts;
import com.microgram.model.Users;
import com.microgram.repository.CommentsRepository;
import com.microgram.repository.PostRepository;
import com.microgram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<CommentsDTO> getCommentsByPostId(Long postId) {
        List<Comments> comments = commentsRepository.findByPostId(postId);
        
        return comments.stream()
            .map(this::mapToCommentsDTO)
            .collect(Collectors.toList());
    }
    
    private CommentsDTO mapToCommentsDTO(Comments comment) {
        return new CommentsDTO(
            comment.getId(),
            comment.getUserId(),
            comment.getCommentContent(),
            comment.getCreatedAt()
        );
    }
    
    public boolean addComment(Long postId, Long userId, String commentContent) {
        if (commentContent == null || commentContent.trim().isEmpty()) {
            return false;
        }
        
        Comments comment = new Comments();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setCommentContent(commentContent.trim());
        
        commentsRepository.save(comment);
        return true;
    }
}