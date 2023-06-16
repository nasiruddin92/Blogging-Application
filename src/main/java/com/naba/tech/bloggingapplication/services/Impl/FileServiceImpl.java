package com.naba.tech.bloggingapplication.services.Impl;

import com.naba.tech.bloggingapplication.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
       String fileName=image.getOriginalFilename();

       String renameFile= UUID.randomUUID().toString().concat(fileName.substring(fileName.lastIndexOf('.')));
       String filePath=path+ File.separator+renameFile;

       //Create Folder is not exists
       File file=new File(path);
       if (!file.exists()){
           file.mkdir();
       }
       //Files Copy
        Files.copy(image.getInputStream(), Paths.get(filePath));

       return renameFile;

    }

    @Override
    public InputStream getResources(String path, String fileName) throws FileNotFoundException {
        InputStream inputStream=new FileInputStream(path+File.separator+fileName);
        return inputStream;
    }
}
