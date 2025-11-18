package com.example.demo.service;

import com.example.demo.model.ChatMember;
import com.example.demo.repository.ChatMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMemberService {
    private final ChatMemberRepository chatMemberRepository;
    @Autowired
    public ChatMemberService(ChatMemberRepository chatMemberRepository) {
        this.chatMemberRepository = chatMemberRepository;
    }

}
