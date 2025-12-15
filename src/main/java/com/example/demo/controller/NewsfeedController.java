package com.example.demo.controller;

import com.example.demo.dto.PostDTO;
import com.example.demo.model.Newsfeed;
import com.example.demo.service.NewsfeedService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/newsfeed")
@AllArgsConstructor
public class NewsfeedController {
    NewsfeedService newsfeedService;
    @GetMapping("/{userId}")
    public ResponseEntity<?> getNewsfeed(@PathVariable Long userId){
        return ResponseEntity.ok().body(newsfeedService.findAllByUserReceived(userId));
    }
}
