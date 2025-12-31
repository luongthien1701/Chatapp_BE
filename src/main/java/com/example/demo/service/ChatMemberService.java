package com.example.demo.service;

import com.example.demo.model.ChatMember;
import com.example.demo.repository.ChatMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ChatMemberService {
    private final ChatMemberRepository chatMemberRepository;
    @Autowired
    public ChatMemberService(ChatMemberRepository chatMemberRepository) {
        this.chatMemberRepository = chatMemberRepository;
    }
    public List<Long> getMembers(Long roomId) {
        return chatMemberRepository.findAllByChatRoomId(roomId)
                .stream()
                .distinct()
                .map(k->k.getUser().getId())
                .toList();
    }

}
