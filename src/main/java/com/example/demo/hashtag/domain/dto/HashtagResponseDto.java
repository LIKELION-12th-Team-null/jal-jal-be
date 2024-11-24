package com.example.demo.hashtag.domain.dto;


import com.example.demo.hashtag.domain.Hashtag;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class HashtagResponseDto {
    private Long id;
    private String hashtagName;


    public HashtagResponseDto(Hashtag hashtag) {
        this.id = hashtag.getId();
        this.hashtagName = hashtag.getHashtagName();
    }
}
