package com.behloleaqil.blog.services;

import com.behloleaqil.blog.entities.Post;
import com.behloleaqil.blog.payloads.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    PostDTO updatePost(PostDTO postDTO, Integer postId,Integer userId, Integer categoryId);

    PostDTO getSinglePost(Integer postId);

    List<PostDTO> getAllPosts();

    void deletePost(Integer postId);

    List<PostDTO> getAllPostByCategory(Integer categoryId);

    List<PostDTO> getAllPostByUser(Integer userId);

    List<PostDTO> searchPost(String keyword);
}
