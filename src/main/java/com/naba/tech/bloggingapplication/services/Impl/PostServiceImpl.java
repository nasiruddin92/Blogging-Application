package com.naba.tech.bloggingapplication.services.Impl;

import com.naba.tech.bloggingapplication.entity.Category;
import com.naba.tech.bloggingapplication.entity.Post;
import com.naba.tech.bloggingapplication.entity.User;
import com.naba.tech.bloggingapplication.exceptions.ResourceNotFoundException;
import com.naba.tech.bloggingapplication.payloads.PostDto;
import com.naba.tech.bloggingapplication.payloads.PostResponse;
import com.naba.tech.bloggingapplication.repositories.CategoryRepository;
import com.naba.tech.bloggingapplication.repositories.PostRepository;
import com.naba.tech.bloggingapplication.repositories.UserRepository;
import com.naba.tech.bloggingapplication.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public PostDto createPost(PostDto postDto,long userId,long categoryId) {

        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        Post post=postDtoToPost(postDto);
        post.setCreateDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        //post.setImageName("default.png");
        return postToPostDto(postRepository.save(post));
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long postId) {
        Post post=postRepository.findById(postId).
                orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        return postToPostDto(postRepository.save(post));
    }

    @Override
    public PostDto getPostById(long postId) {
        Post post = postRepository.findById(postId ).orElseThrow( () -> new ResourceNotFoundException( "Post", "Id", postId ) );
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy,String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> postPage = postRepository.findAll( pageable);

        PostResponse postResponse =new PostResponse();

        postResponse.setPostDtos(postPage.getContent().stream().map(this::postToPostDto).collect( Collectors.toList()));
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements((int)postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
        //return postRepository.findAll(pageable).stream().map(this::postToPostDto).collect( Collectors.toList());
    }

    @Override
    public void deletePostById(long postId) {
        Post post=postRepository.findById(postId).
                orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        postRepository.delete(post);

    }

    @Override
    public List<PostDto> getPostByUser(long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        return postRepository.findByUser(user).stream().map(this::postToPostDto).collect( Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByCategory(long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
        return postRepository.findByCategory(category).stream().map(this::postToPostDto).collect( Collectors.toList());
    }

    @Override
    public List<PostDto> searchByPosts(String keyWord) {
        return postRepository.findByTitleContaining(keyWord).stream().map(this::postToPostDto).collect( Collectors.toList());
    }

    private PostDto postToPostDto(Post post){
        return modelMapper.map(post,PostDto.class);
    }

    private Post postDtoToPost(PostDto postDto){
        return modelMapper.map(postDto,Post.class);
    }
}
