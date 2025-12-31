package com.example.demo.controller;

import com.example.demo.service.NotiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@AllArgsConstructor
public class NotiController {
    private NotiService notiService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getNoti(@PathVariable Long userId){
        return ResponseEntity.ok().body(notiService.findAll(userId));
    }
}
