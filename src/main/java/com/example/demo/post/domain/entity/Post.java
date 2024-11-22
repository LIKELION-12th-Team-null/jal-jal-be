package com.example.demo.post.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.demo.member.domain.entity.Member;

@Entity
@Getter
@Setter
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

    @Column(name = "like_count", nullable = false)
    private int likeCount;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
