package com.naba.tech.bloggingapplication.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {

    private long id;
    private String content;
//    private UserDto user;
//    private PostDto post;
}
