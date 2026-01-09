package com.example.demo.service;

import com.example.demo.dto.post.PostImageRequest;
import com.example.demo.model.Newsfeed;
import com.example.demo.model.Post_Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.NewsfeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
