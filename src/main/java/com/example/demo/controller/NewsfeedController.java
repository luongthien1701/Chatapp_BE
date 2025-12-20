package com.example.demo.controller;

import com.example.demo.dto.PostDTO;
import com.example.demo.dto.UpdatePost;
import com.example.demo.model.Newsfeed;
import com.example.demo.service.NewsfeedService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/newsfeed")
@AllArgsConstructor
public class NewsfeedController {
    NewsfeedService newsfeedService;
    @GetMapping("/{userId}")
    public ResponseEntity<?> getNewsfeed(@PathVariable Long userId){
        return ResponseEntity.ok().body(newsfeedService.findAllByUserReceived(userId));
    }
    @PostMapping
    public ResponseEntity<?> saveNewsfeed(@RequestBody PostDTO postDTO){
        long id=newsfeedService.addPost(postDTO);
        return ResponseEntity.ok().body(Map.of("id",id));
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteNewsfeed(@PathVariable int postId){
        newsfeedService.deletePost(postId);
        return ResponseEntity.ok().body(Map.of("message","success"));
    }
    @PutMapping("/addlike")
    public ResponseEntity<?> addLike(@RequestBody UpdatePost  updatePost){
        newsfeedService.addLikePost(updatePost);
        return ResponseEntity.ok().body(Map.of("message","success"));
    }
    @PutMapping("/removelike")
    public ResponseEntity<?> dscLike(@RequestBody UpdatePost  updatePost){
        newsfeedService.dscLikePost(updatePost);
        return ResponseEntity.ok().body(Map.of("message","success"));
    }

}
