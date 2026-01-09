package com.example.demo.controller;

import io.agora.media.RtcTokenBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/agora")
public class AgoraController {
    private static final String app_id="c43cb3a3918a4d6f9ce96e5f3cbd5aab";
    private static final String app_certificate="122489cbce4a48b4a2917b0495391ce9";
    @GetMapping("/token")
    public Map<String,String> getToken(@RequestParam String channelName, @RequestParam int userId) throws Exception {
        int expirationTimeInSeconds = 3600;
        int currentTimeInSeconds = (int) (System.currentTimeMillis() / 1000);
        int privilegeExpirationTimeInSeconds = expirationTimeInSeconds + currentTimeInSeconds;
        RtcTokenBuilder tokenBuilder = new RtcTokenBuilder();
        String token=tokenBuilder.buildTokenWithUid(app_id,app_certificate,channelName,userId, RtcTokenBuilder.Role.Role_Publisher,privilegeExpirationTimeInSeconds);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        return map;
    }
}
