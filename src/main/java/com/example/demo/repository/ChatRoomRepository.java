package com.example.demo.repository;

import com.example.demo.dto.ChatRoomDTO;
import com.example.demo.model.ChatMember;
import com.example.demo.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    @Query("""
    select new com.example.demo.dto.ChatRoomDTO(
    r.id, r.name, r.avatarUrl,
    m.content,
    r.uniquekey,
    m.sentAt)
    from ChatRoom r
    join r.members rm
    left join Message m on m.id = r.lastMessageId
    where rm.user.id = :userId
    """)
    List<ChatRoomDTO> findAllByUserId(@Param("userId") Long userId);

    @Query(value = """
    SELECT cr.*
    FROM chat_rooms cr
   WHERE cr.id=:userId and cr.name like CONCAT('%', :like, '%')
""",nativeQuery = true)
    List<ChatRoom> findByNameContaining(@Param("userId") Long userId,@Param("like") String like);

    @Query(value = """
    SELECT cr.*
    FROM chat_rooms cr
    WHERE cr.uniqueKey=:uniqueKey
""",nativeQuery = true)
    ChatRoom findByUniqueKey(@Param("uniqueKey") String uniqueKey);
}
