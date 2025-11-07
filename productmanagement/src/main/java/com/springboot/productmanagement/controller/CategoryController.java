package com.springboot.productmanagement.controller;

import com.springboot.productmanagement.dto.CategoryRequestDto;
import com.springboot.productmanagement.dto.CategoryResponseDto;
import com.springboot.productmanagement.dto.ErrorResponse;
import com.springboot.productmanagement.dto.ProductRequestDto;
import com.springboot.productmanagement.exception.EntityExistsException;
import com.springboot.productmanagement.exception.EntityNotFoundException;
import com.springboot.productmanagement.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @RequestBody CategoryRequestDto categoryRequest,
            @PathVariable Long id
    ){
        try{
            this.categoryService.updateCategory(id, categoryRequest);
            return ResponseEntity.ok("Successfully updated Product");
        } catch(RuntimeException e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            HttpStatus.CONFLICT.value(),
                            e.getMessage()
                    )
            );
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(
            @RequestBody CategoryRequestDto categoryRequest
    ){
        try{
            this.categoryService.createCategory(categoryRequest);
            return ResponseEntity.ok("Successfully created Category");
        }catch(EntityExistsException e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            HttpStatus.CONFLICT.value(),
                            e.getMessage()
                    )
            );
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> retrieveAll(){
        List<CategoryResponseDto> categories = this.categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){
        try{
            this.categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()
                    )
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveOne(@PathVariable Long id){
        try{
            return ResponseEntity.ok(this.categoryService.getCategoryById(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()
                    )
            );
        }
    }
}
