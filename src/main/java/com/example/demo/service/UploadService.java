package com.example.demo.service;

import com.example.demo.model.Newsfeed;
import com.example.demo.model.Post_Image;
import com.example.demo.model.Users;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.NewsfeedRepository;
import com.example.demo.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadService {
    UsersRepository usersRepository;
    NewsfeedRepository newsfeedRepository;
    ImageRepository imageRepository;
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
        Files.write(path,file.getBytes());
        return "/uploads/"+folder+"/"+filename;
    }
}
