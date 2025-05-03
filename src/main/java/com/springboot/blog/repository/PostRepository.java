package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
// NO need to annotate it with @Repository because
//SimpleJpaRepository is implementing JpaRepositoryImplementation which is extending JpaRepository

public interface PostRepository extends JpaRepository<Post, Long> {
    //
}
