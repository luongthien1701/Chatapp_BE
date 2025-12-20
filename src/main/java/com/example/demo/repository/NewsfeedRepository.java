package com.example.demo.repository;

import com.example.demo.model.Newsfeed;
import com.example.demo.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsfeedRepository extends JpaRepository<Newsfeed,Long> {
    List<Newsfeed> findAllByUserId(Long userId);
    Newsfeed findById(long id);
}
