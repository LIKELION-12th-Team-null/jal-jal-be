package com.example.demo.post.domain.entity;

import com.example.demo.hashtag.domain.PostHashtag;
import jakarta.persistence.*;
import lombok.*;
import com.example.demo.member.domain.entity.Member;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.EAGER)
    private Set<PostHashtag> postHashtags = new HashSet<>();

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addPostHashtag(PostHashtag postHashtag) {
        this.postHashtags.add(postHashtag);
        postHashtag.setPost(this);
    }
}
