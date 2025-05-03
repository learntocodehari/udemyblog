package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//@Repository
// NO need to annotate it with @Repository because
//SimpleJpaRepository is implementing JpaRepositoryImplementation which is extending JpaRepository

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(long postId);


}
