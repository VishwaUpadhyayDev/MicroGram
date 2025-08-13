package com.microgram.repository;

import com.microgram.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByPostId(Long postId);
    Optional<Likes> findByPostIdAndUserId(Long postId, Long userId);
    void deleteByPostId(Long postId);
}