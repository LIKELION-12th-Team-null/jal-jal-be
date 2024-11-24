package com.example.demo.hashtag.domain;

import com.example.demo.post.domain.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Entity
public class PostHashtag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable=false)
    @JsonIgnore
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id", nullable=false)
    private Hashtag hashtag;

    public PostHashtag(Post post, Hashtag hashtag) {
        this.post = post;
        this.hashtag = hashtag;
    }
}
