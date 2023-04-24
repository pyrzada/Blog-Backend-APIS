package com.behloleaqil.blog.controllers;

import com.behloleaqil.blog.entities.Comment;
import com.behloleaqil.blog.payloads.APIResponse;
import com.behloleaqil.blog.payloads.CommentDTO;
import com.behloleaqil.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}/comments")
    public ResponseEntity<CommentDTO> addComment(
            @RequestBody CommentDTO comment,
            @PathVariable("postId") int postId,
            @PathVariable("userId") int userId
    ) {
        CommentDTO commentDTO = this.commentService.createComment(comment, userId, postId);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<APIResponse> deleteComment(@PathVariable("commentId") Integer commentID) {
        this.commentService.deleteComment(commentID);
        return new ResponseEntity<>(new APIResponse("Comment has been deleted", true), HttpStatus.OK);
    }
}
