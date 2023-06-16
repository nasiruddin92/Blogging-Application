package com.naba.tech.bloggingapplication.controllers;

import com.naba.tech.bloggingapplication.payloads.FileResponse;
import com.naba.tech.bloggingapplication.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/upload")
    ResponseEntity<FileResponse> uploadImage(
            @RequestParam("image") MultipartFile image){
        String fileName=null;
        try {
             fileName = fileService.uploadImage( path, image );
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse( null,"Image is not uploaded due to server error!!" ),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new FileResponse(fileName,"Image uploaded successfully !!"), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{images}/{imageName}",produces =MediaType.IMAGE_JPEG_VALUE )
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {

        InputStream resources = fileService.getResources( path,imageName );
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resources,response.getOutputStream());

    }

}
