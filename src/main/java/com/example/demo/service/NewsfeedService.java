package com.example.demo.service;

import com.example.demo.dto.PostDTO;
import com.example.demo.dto.SenderInfo;
import com.example.demo.dto.UpdatePost;
import com.example.demo.model.Post_Like;
import com.example.demo.model.Newsfeed;
import com.example.demo.model.Users;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.NewsfeedRepository;
import com.example.demo.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class NewsfeedService {
    private final FriendRepository friendRepository;
    NewsfeedRepository newsfeedRepository;
    private final UsersRepository usersRepository;
    private final LikeRepository likeRepository;
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
                if (!newsfeed1.isDeleted()) {
                    PostDTO postDTO = new PostDTO();
                    postDTO.setId(newsfeed1.getId());
                    postDTO.setSender(new SenderInfo(newsfeed1.getUser().getId(), newsfeed1.getUser().getDisplayName(), newsfeed1.getUser().getAvatarUrl()));
                    postDTO.setContent(newsfeed1.getContent());
                    postDTO.setFavorite(newsfeed1.getFavorite());
                    postDTO.setImage(newsfeed1.getImageUrl());
                    postDTO.setCreateAt(newsfeed1.getCreateAt());
                    postDTO.setComments(newsfeed1.getComments());
                    postDTO.set_liked(likeRepository.existsByNewsfeedIdAndUserId(newsfeed1.getId(), userId));
                    list.add(postDTO);
                }
            }
        }
        return list;
    }

    public long addPost(PostDTO postDTO){
        Newsfeed nf=new Newsfeed();
        Users user=usersRepository.findUserById(postDTO.getSender().getId());
        nf.setComments(0L);
        nf.setContent(postDTO.getContent());
        nf.setFavorite(0L);
        nf.setUser(user);
        nf.setCreateAt(postDTO.getCreateAt());
        nf.setImageUrl(postDTO.getImage());
        nf.setDeleted(false);
        newsfeedRepository.save(nf);
        return nf.getId();
    }
    public void addLikePost(UpdatePost updatePost)
    {
        if (likeRepository.existsByNewsfeedIdAndUserId(updatePost.getPostId(), updatePost.getUserId()))
        {
            throw new RuntimeException("Đã xảy ra lỗi");
        }
        Newsfeed nf=newsfeedRepository.findById(updatePost.getPostId());
        Users us=usersRepository.findUserById((long)updatePost.getUserId());
        if (nf==null) throw new RuntimeException("Lỗi bài viết có thể không tồn tại");
        nf.setFavorite(nf.getFavorite()+1);
        Post_Like like=new Post_Like();
        like.setNewsfeed(nf);
        like.setUser(us);
        likeRepository.save(like);
        newsfeedRepository.save(nf);
    }
    public void dscLikePost(UpdatePost updatePost)
    {
        System.out.println(updatePost.getPostId()+""+updatePost.getUserId());
        if (!likeRepository.existsByNewsfeedIdAndUserId(updatePost.getPostId(),updatePost.getUserId()))
        {
            throw new RuntimeException("Đã xảy ra lỗi");
        }
        likeRepository.deleteByNewsfeedIdAndUserId(updatePost.getPostId(),updatePost.getUserId());
        Newsfeed nf=newsfeedRepository.findById(updatePost.getPostId());
        if (nf==null) throw new RuntimeException("Lỗi bài viết có thể không tồn tại");
        nf.setFavorite(nf.getFavorite()-1);
        newsfeedRepository.save(nf);
    }
    public void deletePost(int postId)
    {
        Newsfeed nf=newsfeedRepository.findById(postId);
        nf.setDeleted(true);
        newsfeedRepository.save(nf);
    }
}
