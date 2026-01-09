package com.example.demo.repository;

import com.example.demo.dto.noti.NotificationDTO;
import com.example.demo.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("""
    select new com.example.demo.dto.noti.NotificationDTO(
        n.id,
        n.title,
        n.status,
        n.createdAt,
        new com.example.demo.dto.user.UserSummary(
            n.senderId.id,
            n.senderId.displayName,
            n.senderId.avatarUrl
        ),
        n.receiverId,
        ""
    )
    from Notification n
    where n.receiverId = :receiverId
""")
    List<NotificationDTO> findAllByReceiver(@Param("receiverId") Long receiverId);

}
