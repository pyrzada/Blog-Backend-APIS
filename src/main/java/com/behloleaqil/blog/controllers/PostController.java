package com.behloleaqil.blog.controllers;

import com.behloleaqil.blog.config.AppConstants;
import com.behloleaqil.blog.payloads.APIResponse;
import com.behloleaqil.blog.payloads.PostDTO;
import com.behloleaqil.blog.payloads.PostResponse;
import com.behloleaqil.blog.services.FileService;
import com.behloleaqil.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;
    @Value("${project.image}")
    private String path;

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

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchPost(@PathVariable String keywords) {
        List<PostDTO> postDTOList = this.postService.searchPost(keywords);
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection
    ) {
        return new ResponseEntity<>(
                this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection),
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

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("postId") Integer postId
    ) throws IOException {
        PostDTO post = this.postService.getSinglePost(postId);
        String fileName = this.fileService.uploadImage(path, image);
        post.setImageName(fileName);
        return new ResponseEntity<>(this.postService.updatePost(post, postId), HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}
