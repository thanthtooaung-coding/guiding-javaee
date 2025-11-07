package com.springboot.productmanagement.mapper;

import com.springboot.productmanagement.dto.CategoryResponseDto;
import com.springboot.productmanagement.model.Category;

public class CategoryMapper {
    public static CategoryResponseDto toCategoryResponseDto(Category category){
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }
}
