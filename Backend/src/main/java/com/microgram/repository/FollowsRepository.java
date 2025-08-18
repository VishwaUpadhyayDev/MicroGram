package com.microgram.repository;

import com.microgram.model.Follows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowsRepository extends JpaRepository<Follows, Long> {

    Optional<Follows> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    @Query("SELECT f.followingId FROM Follows f WHERE f.followerId = :userId")
    List<Long> findFollowingIdsByFollowerId(@Param("userId") Long userId);

    @Query("SELECT f.followerId FROM Follows f WHERE f.followingId = :userId")
    List<Long> findFollowerIdsByFollowingId(@Param("userId") Long userId);
}