package com.springboot.productmanagement.service.impl;

import com.springboot.productmanagement.dto.CategoryRequestDto;
import com.springboot.productmanagement.dto.CategoryResponseDto;
import com.springboot.productmanagement.exception.EntityExistsException;
import com.springboot.productmanagement.exception.EntityNotFoundException;
import com.springboot.productmanagement.mapper.CategoryMapper;
import com.springboot.productmanagement.model.Category;
import com.springboot.productmanagement.model.Product;
import com.springboot.productmanagement.repository.CategoryRepository;
import com.springboot.productmanagement.service.CategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // Bean Declaration
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void createCategory(CategoryRequestDto categoryRequest) {
        if(categoryRequest != null) {
            if(categoryRepository.existsByName(categoryRequest.name())){
                throw new EntityExistsException("Category with name " + categoryRequest.name() + " already exists");
            }

            Category category = new Category();

            category.setName(categoryRequest.name());
            category.setCreatedAt(LocalDateTime.now());

            categoryRepository.save(category);
        }
    }

    @Override
    public void updateCategory(Long id, CategoryRequestDto categoryRequest) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category with id: " + id + " does not exist"));

        if (categoryRepository.existsByName(categoryRequest.name())
                && existingCategory.getName().equals(categoryRequest.name())) {
            throw new RuntimeException("Change the name or something bruh ;-;");
        }

        existingCategory.setName(categoryRequest.name());
        existingCategory.setUpdatedAt(LocalDateTime.now());

        categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!this.categoryRepository.existsById(id)){
            throw new EntityNotFoundException("category with id: " + id + " does not exist");
        }
        this.categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryResponseDto> getAllCategory() {
        return this.categoryRepository.findAll()
                .stream().map(CategoryMapper::toCategoryResponseDto).toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        if (!this.categoryRepository.existsById(id)){
            throw new EntityNotFoundException("category with id " + id + " does not exist");
        }

        Optional<Category> categoryOptional = this.categoryRepository.findById(id);
        return categoryOptional.map(CategoryMapper::toCategoryResponseDto).orElse(null);
    }
}
