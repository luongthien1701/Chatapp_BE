package com.example.demo.repository;

import com.example.demo.model.Post_Like;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeRepository extends JpaRepository<Post_Like,Long> {
    boolean existsByNewsfeedIdAndUserId(Long postId,Long userId);
    void deleteByNewsfeedIdAndUserId(Long postId,Long userId);
}
