package com.example.demo.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostImageRequest {
    private int  id;
    private int  postId;
    private String imageUrl;
}
