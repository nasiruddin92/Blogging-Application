package com.naba.tech.bloggingapplication.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    public String uploadImage(String path, MultipartFile image) throws IOException;
    public InputStream getResources(String path,String fileName) throws FileNotFoundException;
}
