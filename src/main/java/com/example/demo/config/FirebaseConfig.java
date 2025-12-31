package com.example.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void init() throws Exception {
        FileInputStream serviceAccount=new FileInputStream("D:\\Learn\\ServerChatRealTime\\demo (2).zip_expanded\\demo\\src\\main\\resources\\device-streaming-79c92ab1-firebase-adminsdk-jurzs-5ace45646d.json");
        FirebaseOptions options=FirebaseOptions.builder().
                setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
