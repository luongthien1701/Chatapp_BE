package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.UUID;

@Service
public class UploadService {
    public String save(MultipartFile file,String type,Long ownerId) throws Exception{
        String originalFilename = file.getOriginalFilename();
        String ext=originalFilename.substring(originalFilename.lastIndexOf("."));
        String folder= switch (type)
        {
            case "avatar" -> "avatar";
            case "post" -> "posts/post_"+ownerId;
            case "chat" ->"messages/room_"+ownerId;
            default -> throw new RuntimeException("Đã xảy ra lỗi khi xử lý ảnh");
        };
        String filename= UUID.randomUUID()+ext;
        Path path = Paths.get("uploads",folder,filename);
        Files.createDirectories(path.getParent());
        Files.write(path,file.getBytes(), StandardOpenOption.APPEND);
        return "/uploads/"+folder+"/"+filename;
    }
}
