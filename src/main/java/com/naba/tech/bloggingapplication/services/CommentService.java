package com.naba.tech.bloggingapplication.services;

import com.naba.tech.bloggingapplication.payloads.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,long userId,long postId);
    //void updateComment(CommentDto commentDto);
    void deleteComment(long id);
}
