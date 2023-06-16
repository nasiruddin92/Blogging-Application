package com.naba.tech.bloggingapplication.payloads;

import com.naba.tech.bloggingapplication.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 3, max = 80,message ="Min Length 3 And Max Length 80 !!")
    private String title;
    private String content;
    private String imageName;
    private Date createDate;
    private CategoryDto category;
    private UserDto user;
    private List<CommentDto> comments=new ArrayList<>();
}
