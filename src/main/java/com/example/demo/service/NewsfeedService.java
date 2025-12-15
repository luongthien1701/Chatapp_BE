package com.example.demo.service;

import com.example.demo.dto.PostDTO;
import com.example.demo.dto.SenderInfo;
import com.example.demo.model.Friend;
import com.example.demo.model.Newsfeed;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.NewsfeedRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class NewsfeedService {
    private final FriendRepository friendRepository;
    NewsfeedRepository newsfeedRepository;
    public List<PostDTO> findAllByUserReceived(Long userId) {
        List<PostDTO> list = new ArrayList<>();
        List<Long> friend1 = friendRepository.findFriendsWhereUserIsSender(userId);
        List<Long> friend2 = friendRepository.findFriendsWhereUserIsReceiver(userId);
        Set<Long> listFriends=new HashSet<>();
        listFriends.addAll(friend1);
        listFriends.addAll(friend2);
        System.out.println(newsfeedRepository.findAllByUserId(1L));
        for(Long friendId:listFriends){
            List<Newsfeed> newsfeed=newsfeedRepository.findAllByUserId(friendId);
            for(Newsfeed newsfeed1:newsfeed){
                PostDTO postDTO=new PostDTO();
                postDTO.setId(newsfeed1.getId());
                postDTO.setSender(new SenderInfo(newsfeed1.getUser().getId(),newsfeed1.getUser().getDisplayName(),newsfeed1.getUser().getAvatarUrl()));
                postDTO.setContent(newsfeed1.getContent());
                postDTO.setFavorite(newsfeed1.getFavorite());
                postDTO.setImage(newsfeed1.getImageUrl());
                postDTO.setCreateAt(newsfeed1.getCreateAt());
                list.add(postDTO);
            }
        }
        return list;
    }
    public void save(Newsfeed newsfeed) {
        newsfeedRepository.save(newsfeed);
    }
}
