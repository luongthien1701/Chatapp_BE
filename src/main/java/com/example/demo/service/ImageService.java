package com.example.demo.service;

import com.example.demo.dto.PostImageRequest;
import com.example.demo.model.Newsfeed;
import com.example.demo.model.Post_Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.NewsfeedRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ImageService {
    private final ImageRepository imageRepository;
    private final NewsfeedRepository newsfeedRepository;
    public void save(PostImageRequest postImageRequest){
        Newsfeed newsfeed=newsfeedRepository.findById(postImageRequest.getPostId());
        Post_Image post_image = new Post_Image();
        post_image.setNewsfeed(newsfeed);
        post_image.setImgUrl(postImageRequest.getImageUrl());
        imageRepository.save(post_image);
    }

}
