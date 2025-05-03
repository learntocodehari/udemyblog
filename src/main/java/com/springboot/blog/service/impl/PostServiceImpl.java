package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    // constructor based dependency injection
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // dto to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        // convert entity to dto and return
        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {

        // create pageable instance
      /**
       *
       *     Pageable pageable = PageRequest.of(pageNumber, pageSize);
       *          Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
       *           Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
       * */

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);


        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(this::mapToDto).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setLast(posts.isLast());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setPageSize(posts.getTotalPages());
        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        return mapToDto(postRepository.save(post));
    }

    @Override
    public PostDto deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.deleteById(id);
        return mapToDto(post);
    }

    private PostDto mapToDto(Post post) {
        // convert entity to dto and return

        return modelMapper.map(post, PostDto.class);
    }

    private Post mapToEntity(PostDto postDto) {
        // Convert dto to entity
        Post post = modelMapper.map(postDto, Post.class);

        // 🔁 Fix the back-reference for each comment
        if (post.getComments() != null) {
            for (Comment comment : post.getComments()) {
                comment.setPost(post);  // ✅ Crucial step
            }
        }

        return post;
    }

}
