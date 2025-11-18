package com.example.demo.controller;

import com.example.demo.dto.ChatRoomDTO;
import com.example.demo.dto.MessengerResponse;
import com.example.demo.dto.SelectSearch;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.Users;
import com.example.demo.service.ChatRoomService;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class GlobalController {
    final UserService userService;
    final MessageService messageService;
    final ChatRoomService chatRoomService;
    @GetMapping("/search/{userId}/{like}")
    public ResponseEntity<?> findAllByLikeDisplayName(@PathVariable Long userId,@PathVariable String like ) {
        List<SelectSearch> sl=new ArrayList<>();
        List<Users> users=userService.findAllByLikeDisplayName(like);
        List<MessengerResponse> messlist=messageService.findLikeContent(userId,like);
        List<ChatRoom> rooms=chatRoomService.findbylikeName(userId,like);
        users.forEach(user->{
        sl.add(new SelectSearch("user",user.getId(),user.getDisplayName(),user.getAvatarUrl(),null,null,null));
        });
        messlist.forEach(mess->{
            sl.add(new SelectSearch("messager",mess.getId(),mess.getSender().getName(),null,mess.getContent(),mess.getGroupId(),mess.getGroupName()));
        });
        rooms.forEach(room->{
            sl.add(new SelectSearch("room",null,null,room.getAvatarUrl(),null,room.getId(),room.getName()));
        });
        return ResponseEntity.ok().body(sl);
    }
}
