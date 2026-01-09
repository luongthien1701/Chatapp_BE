package com.example.demo.controller;

import com.example.demo.dto.noti.TokenNotiRequest;
import com.example.demo.service.NotiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notification")
@AllArgsConstructor
public class NotiController {
    private NotiService notiService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getNoti(@PathVariable Long userId){
        return ResponseEntity.ok().body(notiService.findAll(userId));
    }
    @GetMapping("/token")
    public ResponseEntity<?> getToken(@RequestParam Long userId){
        return  ResponseEntity.ok().body(notiService.getToken(userId));
    }
    @PostMapping("/token")
    public ResponseEntity<?> updateToken(@RequestBody TokenNotiRequest tokenNotiRequest){
        notiService.setToken(tokenNotiRequest.getUserId(), tokenNotiRequest.getToken());
        return ResponseEntity.ok().body(Map.of("message","success"));
    }
}
