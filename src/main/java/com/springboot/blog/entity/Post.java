package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // we need this because hibernate uses proxies to create objects.

@Entity
@Table(name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    // this post is present in Comment entity
    // CascadeType.ALL -> whenever we save parent then it's child will automatically will be saved
    // orphanRemoval = true -> whenever we remove the parent then it's child will be also removed.
    private Set<Comment> comments = new HashSet<>();
}
