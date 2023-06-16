package com.naba.tech.bloggingapplication.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDtoMapper {

    private String title;
    private String content;
}
