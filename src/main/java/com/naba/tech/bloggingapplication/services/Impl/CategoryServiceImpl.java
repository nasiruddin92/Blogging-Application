package com.naba.tech.bloggingapplication.services.Impl;

import com.naba.tech.bloggingapplication.entity.Category;
import com.naba.tech.bloggingapplication.exceptions.ResourceNotFoundException;
import com.naba.tech.bloggingapplication.payloads.CategoryDto;
import com.naba.tech.bloggingapplication.repositories.CategoryRepository;
import com.naba.tech.bloggingapplication.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=categoryDtoToCategory(categoryDto);
        return categoryToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long categoryId) {

        Category category=categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        return categoryToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto getCategoryById(long categoryId) {
        Category category=categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        return categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories=categoryRepository.findAll();
//        List<CategoryDto> categoryDtos=categories.stream().map(category ->
//                categoryToCategoryDto(category)).collect( Collectors.toList());
//        return categoryDtos;
        return categories.stream().map( this::categoryToCategoryDto).collect( Collectors.toList());
    }

    @Override
    public void deleteUserById(long categoryId) {

        Category category=categoryRepository.findById(categoryId).
                orElseThrow( ()->new ResourceNotFoundException("Category","id",categoryId));
        categoryRepository.delete(category);

    }

    private CategoryDto categoryToCategoryDto(Category category){
       return  modelMapper.map(category,CategoryDto.class);

    }

    private Category categoryDtoToCategory(CategoryDto categoryDto){
        return modelMapper.map(categoryDto,Category.class);
    }
}
