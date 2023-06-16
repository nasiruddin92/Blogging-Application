package com.naba.tech.bloggingapplication.services.Impl;

import com.naba.tech.bloggingapplication.entity.Comment;
import com.naba.tech.bloggingapplication.entity.Post;
import com.naba.tech.bloggingapplication.entity.User;
import com.naba.tech.bloggingapplication.exceptions.ResourceNotFoundException;
import com.naba.tech.bloggingapplication.payloads.CommentDto;
import com.naba.tech.bloggingapplication.payloads.PostDto;
import com.naba.tech.bloggingapplication.payloads.UserDto;
import com.naba.tech.bloggingapplication.repositories.CommentRepository;
import com.naba.tech.bloggingapplication.repositories.PostRepository;
import com.naba.tech.bloggingapplication.repositories.UserRepository;
import com.naba.tech.bloggingapplication.services.CommentService;
import com.naba.tech.bloggingapplication.services.PostService;
import com.naba.tech.bloggingapplication.services.UserService;
import javafx.geometry.Pos;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public CommentDto createComment(CommentDto commentDto,long userId ,long postId) {
        //Post post =modelMapper.map(postService.getPostById(postId),Post.class); ;
        //User user = modelMapper.map(userService.getUserById( userId),User.class);
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        Comment comment = modelMapper.map(commentDto, Comment.class );
        comment.setPost(post);
        comment.setUser(user);
        return modelMapper.map(commentRepository.save(comment),CommentDto.class);
    }

    @Override
    public void deleteComment(long id) {

        Comment comment = commentRepository.findById( id ).orElseThrow( () -> new ResourceNotFoundException( "Comment", "Id", id) );
        commentRepository.delete(comment);

    }
}
