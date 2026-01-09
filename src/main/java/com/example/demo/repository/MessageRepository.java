package com.example.demo.repository;

import com.example.demo.dto.chat.MessageDTO;
import com.example.demo.dto.chat.MessengerResponse;
import com.example.demo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    //    List<Message> findAllByChatRoom_Id(@Param("chat_id") Long chatRoomId);
    List<MessengerResponse> findAllByChatRoom_Id(@Param("chat_id") Long chatRoomId);

    @Query(value = """
                SELECT mess.id,mess.content,mess.file_url,mess.type,mess.status,mess.sent_at,mess.edit_at,new com.example.demo.dto.user.UserSummary(
                        mess.sender_id,
                        null,
                        null
                    ),mess.chat_id,cr.name
                FROM messages mess
                JOIN chat_rooms cr ON mess.chat_id = cr.id
                WHERE mess.sender_id = :userId
                  AND mess.content LIKE CONCAT('%', :like, '%')
            """, nativeQuery = true)
    List<MessageDTO> findMessageInChatroom(@Param("userId") Long userId, @Param("like") String like);

    Message save(Message message);
}
