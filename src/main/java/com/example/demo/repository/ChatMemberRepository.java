package com.example.demo.repository;

import com.example.demo.model.ChatMember;
import com.example.demo.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMemberRepository extends JpaRepository<ChatMember,Long> {
    @Query(value = """
            select cm.*
            from chat_members cm
            where cm.chat_id=:roomId
            """,nativeQuery = true)
    List<ChatMember> findAllByChatRoomId(@Param("roomId") Long roomId);
}
