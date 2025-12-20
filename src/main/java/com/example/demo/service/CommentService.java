package com.example.demo.service;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.SenderInfo;
import com.example.demo.model.Comment;
import com.example.demo.model.Newsfeed;
import com.example.demo.model.Users;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.NewsfeedRepository;
import com.example.demo.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;
    private final NewsfeedRepository newsfeedRepository;
    public List<CommentDTO> findAllByNewsfeed_Id(Long Newsfeed_Id){
        List<Comment> comments=commentRepository.findAllByNewsfeed_Id(Newsfeed_Id);
        List<CommentDTO> commentDTOS=new ArrayList<>();
        for(Comment comment:comments){
            commentDTOS.add(new CommentDTO(
               comment.getId(),Newsfeed_Id,comment.getContent(),new SenderInfo(comment.getUser().getId(),comment.getUser().getDisplayName(),comment.getUser().getAvatarUrl())
            ));
        }
        return commentDTOS;
    }
    public long addComment(CommentDTO comment){
        Comment comment1=new Comment();
        Users user=usersRepository.findUserById(comment.getSender().getId());
        long postId=comment.getPostId();
        Newsfeed nf=newsfeedRepository.findById(postId);
        comment1.setUser(user);
        comment1.setContent(comment.getContent());
        comment1.setNewsfeed(nf);
        nf.setComments(nf.getComments()+1);
        commentRepository.save(comment1);
        newsfeedRepository.save(nf);
        return comment1.getId();
    }
    public void deleteComment(long commentId){
        Comment cmt=commentRepository.findCommentById(commentId);
        commentRepository.delete(cmt);
    }
    public void updateComment(CommentDTO comment){
        Comment cmt=commentRepository.findCommentById(comment.getId());
        cmt.setContent(comment.getContent());
        commentRepository.save(cmt);
    }
}
