package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // http://localhost:8080/api/posts/17/comments
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("id") long postId,
                                                    @RequestBody CommentDto commentDto) {


        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);

    }

    // http://localhost:8080/api/posts/17/comments
    // get all comments which belongs to post with id = postId
    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("id") long postId) {
        return ResponseEntity.ok(commentService.getAllComments(postId));

    }

    // get comment by id if it belongs to post with id = postId
    // http://localhost:8080/api/posts/17/comments/1

    @GetMapping("/posts/{post-id}/comments/{comment-id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("post-id") long postId,
                                                     @PathVariable("comment-id") long commentId) {

        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    // update  comment by id if it belongs to post with id = postId
    // http://localhost:8080/api/posts/17/comments/1

    @PutMapping("/posts/{post-id}/comments/{comment-id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("post-id") long postId,
                                                        @PathVariable("comment-id") long commentId,
                                                        @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateCommentById(postId, commentId, commentDto));
    }

    // delete comment by id
    // http://localhost:8080/api/posts/17/comments/1
    @DeleteMapping("/posts/{post-id}/comments/{comment-id}")
    public ResponseEntity<CommentDto> deleteCommentById(@PathVariable("post-id") long postId,
                                                        @PathVariable("comment-id") long commentId) {
        return ResponseEntity.ok(commentService.deleteById(postId, commentId));
    }

}




