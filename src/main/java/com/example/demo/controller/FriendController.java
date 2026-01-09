package com.example.demo.controller;

import com.example.demo.dto.friend.FriendRequest;
import com.example.demo.dto.friend.FriendResponse;
import com.example.demo.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friend")
public class FriendController {
    @Autowired
    FriendService friendService;
    @GetMapping("/{userId}")
    public ResponseEntity<List<FriendResponse>> getFriends(@PathVariable("userId") Long userId) {
        return  ResponseEntity.ok(friendService.getAllFriends(userId));
    }
    @PostMapping()
    public ResponseEntity<?> addFriend(@RequestBody FriendRequest  friendRequest) {
        friendService.addFriend(friendRequest);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping()
    public ResponseEntity<?> removeFriend(@RequestBody FriendRequest  friendRequest) {
        friendService.removeFriend(friendRequest);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/block")
    public ResponseEntity<?> blockFriend(@RequestBody FriendRequest friendRequest)
    {
        friendService.blockFriend(friendRequest);
        return ResponseEntity.ok().build();
    }
    @PatchMapping()
    public ResponseEntity<?> AcceptFriend(@RequestBody FriendRequest friendRequest)
    {
        friendService.acceptFriend(friendRequest.getSenderId(), friendRequest.getReceiverId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{userId}/{friendId}")
    public ResponseEntity<?> checkfriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId)
    {
        return ResponseEntity.ok(Map.of("status",friendService.checkFriend(friendId, userId)));
    }
}
