package com.naba.tech.bloggingapplication.services;

import com.naba.tech.bloggingapplication.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, long categoryId);
    CategoryDto getCategoryById(long categoryId);
    List<CategoryDto> getAllCategory();
    void deleteUserById(long categoryId);
}
