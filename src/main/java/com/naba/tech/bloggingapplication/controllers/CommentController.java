package com.naba.tech.bloggingapplication.controllers;


import com.naba.tech.bloggingapplication.payloads.ApiResponse;
import com.naba.tech.bloggingapplication.payloads.CommentDto;
import com.naba.tech.bloggingapplication.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/user/{userId}/post/{postId}")
    ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable long userId,
            @PathVariable long postId){
        return new ResponseEntity<>(commentService.createComment(commentDto, userId, postId ), HttpStatus.CREATED);

    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<ApiResponse> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Delete Successfully !!",true),HttpStatus.OK);
    }

}
