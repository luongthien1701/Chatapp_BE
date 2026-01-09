package com.example.demo.service;

import com.example.demo.dto.chat.ChatRoomDTO;
import com.example.demo.model.ChatMember;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.Message;
import com.example.demo.model.Users;
import com.example.demo.repository.ChatMemberRepository;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@AllArgsConstructor

public class ChatRoomService {
    private ChatRoomRepository chatRoomRepository;
    private UsersRepository usersRepository;
    private MessageRepository messageRepository;
    private ChatMemberRepository chatMemberRepository;
    public List<ChatRoomDTO> GetallRoomById(Long id) {
        List<ChatRoomDTO> rooms = chatRoomRepository.findAllByUserId(id);
        for (ChatRoomDTO dto : rooms) {
            String key = dto.getGroup();
            if (key != null && key.matches("\\d+_\\d+")) {
                String[] parts = key.split("_");
                Long id1 = Long.valueOf(parts[0]);
                Long id2 = Long.valueOf(parts[1]);
                Long otherId = id1.equals(id) ? id2 : id1;

                String otherName = usersRepository.findUserById(otherId).getDisplayName();
                dto.setName(otherName);
            }
        }
        return rooms;
    }
    public List<ChatRoom> findbylikeName(Long userId, String like){
        return  chatRoomRepository.findByNameContaining(userId,like);
    }
    public ChatRoomDTO findbyConvervationId(Long convervationId){
        ChatRoom chatRoom = chatRoomRepository.findById(convervationId).get();
        if(chatRoom!=null){
            ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
            chatRoomDTO.setId(chatRoom.getId());
            chatRoomDTO.setName(chatRoom.getName());
            chatRoomDTO.setAvatarUrl(chatRoom.getAvatarUrl());
            chatRoomDTO.setGroup(chatRoom.getUniquekey());
            if (chatRoom.getLastMessageId() != null) {
                Message msg = messageRepository.findById(chatRoom.getLastMessageId()).get();
                chatRoomDTO.setLastMessage(msg.getContent());
                chatRoomDTO.setSendTime(msg.getSentAt());
            }

            return chatRoomDTO;
        }
        else
            throw new RuntimeException("Đã xảy ra lỗi");
    }
    public Long findRoom(Long userId,Long friendId)
    {
        String uniqueKey=Math.min(userId,friendId)+"_"+Math.max(userId,friendId);
        ChatRoom _room=chatRoomRepository.findByUniqueKey(uniqueKey);
        if (_room!=null){
            return _room.getId();
        }
        ChatRoom room=new ChatRoom();
        Users user=usersRepository.findUserById(userId);
        Users friend=usersRepository.findUserById(friendId);
        room.setAvatarUrl(null);
        room.setLastMessageId(null);
        room.setUniquekey(uniqueKey);
        room.setName(user.getDisplayName()+"-"+friend.getDisplayName());
        room.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        room.setUpdatedAt(new  Timestamp(System.currentTimeMillis()));
        room.setCreatedBy(user);
        chatRoomRepository.save(room);

        ChatMember member1=new ChatMember();
        member1.setUser(user);
        member1.setRole(ChatMember.Role.MEMBER);
        member1.setChatRoom(room);
        member1.setJoinedAt(new  Timestamp(System.currentTimeMillis()));
        ChatMember member2=new ChatMember();
        member2.setUser(friend);
        member2.setRole(ChatMember.Role.MEMBER);
        member2.setChatRoom(room);
        member2.setJoinedAt(new  Timestamp(System.currentTimeMillis()));
        chatMemberRepository.save(member1);
        chatMemberRepository.save(member2);
        return room.getId();
    }
    public Set<Long> getMemberInRoom(Long roomId)
    {
        List<ChatMember> chatMembers=chatMemberRepository.findAllByChatRoomId(roomId);
        Set<Long> members=new HashSet<>();
        for (ChatMember member : chatMembers) {
            members.add(member.getUser().getId());
        }
        return members;
    }
}
