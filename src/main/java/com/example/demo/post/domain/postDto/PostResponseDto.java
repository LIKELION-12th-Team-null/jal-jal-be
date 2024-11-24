package com.example.demo.post.domain.postDto;

import com.example.demo.post.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String content;
    private String nickName;
    private List<String> hashtagNames;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.nickName = post.getAuthor().getNickname();
        this.hashtagNames = post.getPostHashtags().stream()
                .map(postHashtag -> postHashtag.getHashtag().getHashtagName())
                .collect(Collectors.toList());
    }


}
