package com.example.demo.hashtag.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hashtag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String hashtagName;

    @OneToMany(mappedBy = "hashtag")
    private Set<PostHashtag> postHashtags;

    public Hashtag(String hashtagName){
        this.hashtagName = hashtagName;
    }
}
