package com.behloleaqil.blog.services.impl;

import com.behloleaqil.blog.entities.Comment;
import com.behloleaqil.blog.entities.Post;
import com.behloleaqil.blog.entities.User;
import com.behloleaqil.blog.exceptions.ResourceNotFoundException;
import com.behloleaqil.blog.payloads.CommentDTO;
import com.behloleaqil.blog.payloads.PostDTO;
import com.behloleaqil.blog.repositories.CommentRepo;
import com.behloleaqil.blog.repositories.PostRepo;
import com.behloleaqil.blog.repositories.UserRepo;
import com.behloleaqil.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer userId, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        this.commentRepo.save(comment);
        return this.modelMapper.map(comment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
        this.commentRepo.delete(comment);
    }
}
