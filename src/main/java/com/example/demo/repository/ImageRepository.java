package com.example.demo.repository;

import com.example.demo.model.Post_Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Post_Image, Long> {
    List<Post_Image> findAllByNewsfeedId(Long newsfeedId);
}
