package com.behloleaqil.blog.services.impl;

import com.behloleaqil.blog.entities.Category;
import com.behloleaqil.blog.entities.Post;
import com.behloleaqil.blog.entities.User;
import com.behloleaqil.blog.exceptions.ResourceNotFoundException;
import com.behloleaqil.blog.payloads.PostDTO;
import com.behloleaqil.blog.payloads.PostResponse;
import com.behloleaqil.blog.repositories.CategoryRepo;
import com.behloleaqil.blog.repositories.PostRepo;
import com.behloleaqil.blog.repositories.UserRepo;
import com.behloleaqil.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PostRepo postRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        return this.modelMapper.map(this.postRepo.save(post), PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
        Post foundPost = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));

        foundPost.setImageName(postDTO.getImageName());
        foundPost.setCategory(category);
        foundPost.setUser(user);
        foundPost.setTitle(postDTO.getTitle());
        foundPost.setContent(postDTO.getContent());
        foundPost.setAddedDate(new Date());
        this.postRepo.save(foundPost);
        return this.modelMapper.map(foundPost, PostDTO.class);
    }

    @Override
    public PostDTO getSinglePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = null;

        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }
        Page<Post> pagePost = this.postRepo.findAll(
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        sort
                ));
        List<PostDTO> postDTOs = pagePost.getContent().stream().map((singlePost) -> this.modelMapper.map(singlePost, PostDTO.class)).toList();


        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOs);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public void deletePost(Integer postId) {
        Post foundPost = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
        this.postRepo.delete(foundPost);

    }

    @Override
    public List<PostDTO> getAllPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));

        List<Post> posts = this.postRepo.findAllByCategory(category);
        return posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).toList();
    }

    @Override
    public List<PostDTO> getAllPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        List<Post> posts = this.postRepo.findAllByUser(user);
        return posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).toList();
    }

    @Override
    public List<PostDTO> searchPost(String keyword) {
        List<Post> posts = this.postRepo.searchByTitle("%" + keyword + "%");
        return posts.stream().map(singlePost -> this.modelMapper.map(singlePost, PostDTO.class)).toList();
    }
}
