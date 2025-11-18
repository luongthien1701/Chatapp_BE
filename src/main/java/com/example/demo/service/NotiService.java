package com.example.demo.service;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class NotiService {
    NotificationRepository repo;
    UsersRepository usersRepository;
    public List<NotificationDTO> findAll(Long userId){return repo.findAllByReceiver(userId);}
    public void save(NotificationDTO notificationDTO){
        Notification notification = new Notification();
        notification.setTitle(notificationDTO.getTitle());
        notification.setCreatedAt(notificationDTO.getCreatedAt());
        notification.setStatus(false);
        notification.setSenderId(usersRepository.findUserById(notificationDTO.getSender().getId()));
        notification.setReceiverId(notificationDTO.getReceiver());
        repo.save(notification);
    }
}
