package com.springboot.productmanagement.service;

import com.springboot.productmanagement.dto.CategoryRequestDto;
import com.springboot.productmanagement.dto.CategoryResponseDto;
import com.springboot.productmanagement.model.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryRequestDto categoryRequest);
    void updateCategory(Long id, CategoryRequestDto categoryRequest);
    void deleteCategory(Long id);
    List<CategoryResponseDto> getAllCategory();
    CategoryResponseDto getCategoryById(Long id);
}
