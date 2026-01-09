package com.example.demo.controller;

import com.example.demo.dto.post.CommentDTO;
import com.example.demo.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @GetMapping("/{newsfeed_id}")
    public ResponseEntity<?> getComment(@PathVariable("newsfeed_id") Long newsfeed_id)
    {
        return ResponseEntity.ok().body(commentService.findAllByNewsfeed_Id(newsfeed_id));
    }
    @PostMapping()
    public  ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO)
    {
        long id=commentService.addComment(commentDTO);
        return ResponseEntity.ok().body(Map.of("id",id));
    }
    @DeleteMapping("/{commentId}")
    public  ResponseEntity<?> deleteComment(@PathVariable Long commentId)
    {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body(Map.of("message","success"));
    }
    @PutMapping()
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO commentDTO)
    {
        commentService.updateComment(commentDTO);
        return ResponseEntity.ok().body(Map.of("message","success"));
    }
}
