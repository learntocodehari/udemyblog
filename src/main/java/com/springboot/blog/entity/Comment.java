package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY) // many is for comments , one is for post
    @JoinColumn(name = "post-id", nullable = false) // for foreign key constraint
    private Post post;


}