package com.naba.tech.bloggingapplication.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    List<PostDto> postDtos;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean lastPage;
}
