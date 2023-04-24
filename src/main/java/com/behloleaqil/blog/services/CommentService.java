package com.behloleaqil.blog.services;

import com.behloleaqil.blog.payloads.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer userId, Integer postId);

//    PostDTO updateComment(CommentDTO commentDTO, Integer postId, Integer userId, Integer categoryId);

//    PostDTO getSingleComment(Integer commentId);


//    PostResponse getAllComments(int pageNumber, int pageSize, String sortBy, String sortDirection);

    void deleteComment(Integer commentId);

//    List<PostDTO> getCommentsByPost(Integer commentId);

//    List<CommentDTO> getCommentsByUser(Integer commentId);

}
