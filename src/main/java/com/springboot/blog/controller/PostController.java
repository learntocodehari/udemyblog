package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create a blog post
    // http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {

        PostDto createdPost = postService.createPost(postDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/posts?pageNumber=1&pageSize=10&sortBy=title&sortDir=asc
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {


        return ResponseEntity.ok(postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir));

    }

    // http://localhost:8080/api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long postId) {

        return ResponseEntity.ok(postService.getPostById(postId));

    }

    // http://localhost:8080/api/posts/1
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") long postId) {

        return ResponseEntity.ok(postService.updatePost(postDto, postId));

    }

    // http://localhost:8080/api/posts/5
    @DeleteMapping("{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable("id") long postId) {

        return new ResponseEntity<>(postService.deletePost(postId), HttpStatus.OK);
    }


}
