package com.naba.tech.bloggingapplication.services;

import com.naba.tech.bloggingapplication.payloads.PostDto;
import com.naba.tech.bloggingapplication.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,long userId,long categoryId);
    PostDto updatePostById(PostDto postDto,long postId);
    PostDto getPostById(long postId);
    PostResponse getAllPost(int pageNumber, int pageSize,String sortBy,String sortDir);
    void deletePostById(long postId);
    List<PostDto> getPostByUser(long userId);
    List<PostDto> getPostByCategory(long categoryId);
    List<PostDto> searchByPosts(String keyWord);
}
