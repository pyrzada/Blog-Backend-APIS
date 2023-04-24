package com.behloleaqil.blog.controllers;

import com.behloleaqil.blog.payloads.APIResponse;
import com.behloleaqil.blog.payloads.PostDTO;
import com.behloleaqil.blog.payloads.PostResponse;
import com.behloleaqil.blog.services.PostService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/users/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDTO createdPost = this.postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}/category/{categoryId}/posts/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId, @PathVariable Integer postId) {
        PostDTO updatePost = this.postService.updatePost(postDTO, postId, userId, categoryId);
        return new ResponseEntity<>(updatePost, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDTO> postDTOList = this.postService.getAllPostByUser(userId);
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<PostDTO> postDTOList = this.postService.getAllPostByCategory(categoryId);
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumebr,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(
                this.postService.getAllPosts(pageNumebr, pageSize),
                HttpStatus.OK
        );
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getAllPosts(@PathVariable Integer postId) {
        PostDTO postDTO = this.postService.getSinglePost(postId);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<APIResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new APIResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

}
