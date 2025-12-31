package com.example.demo.repository;

import com.example.demo.model.Notification_Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTokenRepository extends JpaRepository<Notification_Token,Long> {
    Notification_Token findByUserId(Long userId);
}
