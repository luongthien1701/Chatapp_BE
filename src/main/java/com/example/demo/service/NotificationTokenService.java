package com.example.demo.service;

import com.example.demo.model.Notification_Token;
import com.example.demo.repository.NotificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTokenService {
    private NotificationTokenRepository notificationTokenRepository;
    public String getToken(Long userId) {
        return notificationTokenRepository.findByUserId(userId).getToken();
    }
    public void setToken(Long userId, String token) {
        Notification_Token notificationToken = notificationTokenRepository.findByUserId(userId);
        notificationToken.setToken(token);
        notificationTokenRepository.save(notificationToken);
    }
}
