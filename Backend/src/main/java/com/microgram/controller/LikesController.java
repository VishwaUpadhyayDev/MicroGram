package com.microgram.controller;

import com.microgram.dto.LikesDTO;
import com.microgram.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikesController {

    @Autowired
    private LikesService likesService;

    @GetMapping("/post/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getLikesByPostId(@PathVariable Long postId) {
        try {
            List<LikesDTO> likes = likesService.getLikesByPostId(postId);
            if (likes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(likes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
    @PostMapping("/post/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addLike(@PathVariable Long postId, @RequestParam Long userId) {
        try {
            boolean success = likesService.addLike(postId, userId);
            
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Like added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add like");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
    @DeleteMapping("/post/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteLike(@PathVariable Long postId, @RequestParam Long userId) {
        try {
            boolean success = likesService.deleteLike(postId, userId);
            
            if (success) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}