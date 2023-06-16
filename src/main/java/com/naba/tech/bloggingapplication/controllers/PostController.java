package com.naba.tech.bloggingapplication.controllers;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naba.tech.bloggingapplication.config.AppConstraints;
import com.naba.tech.bloggingapplication.payloads.*;
import com.naba.tech.bloggingapplication.services.FileService;
import com.naba.tech.bloggingapplication.services.PostService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;

    private ObjectMapper objectMapper=new ObjectMapper();

    @Value("${project.image}")
    private String path;




    @PostMapping("/user/{userId}/category/{categoryId}")
    ResponseEntity<PostDto> createPost(@RequestParam("file") MultipartFile file,
            @RequestParam("postDt") String postDt,
                                    @PathVariable long userId,
                                    @PathVariable long categoryId){
        PostDtoMapper postDtoMapper=null;
        PostDto postDto=null;
            try {
                postDto = objectMapper.readValue( postDt, PostDto.class );
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                System.out.println("Object can't format into json !!");
            }

        try {
            postDto.setImageName(fileService.uploadImage(path,file));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image upload failed !!");
        }

        return new ResponseEntity<PostDto>(postService.createPost(postDto,userId,categoryId), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto,@PathVariable long postId){
        return ResponseEntity.ok(postService.updatePostById(postDto,postId));
    }

    @GetMapping("/")
    ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value ="pageNumber", defaultValue = AppConstraints.PAGE_NUMBER,required =false) int pageNumber,
            @RequestParam(value ="pageSize",defaultValue =AppConstraints.PAGE_SIZE,required =false) int pageSize,
            @RequestParam(value ="sortBy",defaultValue =AppConstraints.SORT_BY,required =false) String sortBy,
            @RequestParam(value ="sortDir",defaultValue =AppConstraints.SORT_DIRE,required =false) String sortDir){
        return ResponseEntity.ok(postService.getAllPost(pageNumber,pageSize,sortBy,sortDir));
    }

    @GetMapping("/{postId}")
    ResponseEntity<PostDto> getPostById(@PathVariable long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @DeleteMapping("/{postId}")
    ResponseEntity<ApiResponse> deletePostById(@PathVariable long postId){
        postService.deletePostById(postId );
        return new  ResponseEntity<ApiResponse>(new ApiResponse("User Delete Successfully !!",true),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<List<PostDto>> getPostByUser(@PathVariable long userId){
        return ResponseEntity.ok(postService.getPostByUser(userId));
    }

    @GetMapping("/category/{categoryId}")
    ResponseEntity<List<PostDto>> etPostByCategory(@PathVariable long categoryId){
        return ResponseEntity.ok(postService.getPostByCategory(categoryId));
    }

    @GetMapping("/search/{keyWord}")
    ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable String keyWord){
        return ResponseEntity.ok(postService.searchByPosts(keyWord));
    }

    @PostMapping("/image/upload/{postId}")
    ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("imageName") MultipartFile imageName,
            @PathVariable long postId) throws IOException {
        String uploadImageName = fileService.uploadImage( path, imageName );
        PostDto post = postService.getPostById( postId );
        post.setImageName(uploadImageName);
        PostDto updatePostDto= postService.updatePostById( post, post.getId() );
        return new ResponseEntity<>(updatePostDto,HttpStatus.OK);

    }

    @GetMapping(value = "/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE )
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {

        InputStream resources = fileService.getResources( path,imageName );
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resources,response.getOutputStream());

    }
}
