package com.naba.tech.bloggingapplication.controllers;


import com.naba.tech.bloggingapplication.payloads.ApiResponse;
import com.naba.tech.bloggingapplication.payloads.CategoryDto;
import com.naba.tech.bloggingapplication.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        return new  ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto,categoryId));
        //return new ResponseEntity<>(categoryService.updateCategory(categoryDto,categoryId),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    ResponseEntity<CategoryDto> getCategoryById(@PathVariable long categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @GetMapping("/")
    ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @DeleteMapping(("/{categoryId}"))
    ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable long categoryId){
        categoryService.deleteUserById(categoryId);
        return new  ResponseEntity<ApiResponse>(new ApiResponse("Category Delete Successfully !!",true),HttpStatus.OK);
    }
}
