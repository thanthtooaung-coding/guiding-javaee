package com.springboot.productmanagement.controller;


import com.springboot.productmanagement.dto.ErrorResponse;
import com.springboot.productmanagement.dto.ProductRequestDto;
import com.springboot.productmanagement.dto.ProductResponseDto;
import com.springboot.productmanagement.exception.EntityExistsException;
import com.springboot.productmanagement.exception.EntityNotFoundException;
import com.springboot.productmanagement.model.Product;
import com.springboot.productmanagement.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
            @RequestBody ProductRequestDto productRequestDto
    ){
        try {
            this.productService.createProduct(productRequestDto);
            return ResponseEntity.ok("Successfully Created Product");
        }catch(EntityExistsException e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            HttpStatus.CONFLICT.value(),
                            e.getMessage()
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @RequestBody ProductRequestDto productRequest,
            @PathVariable Long id
    ){
        try{
            this.productService.updateProduct(id, productRequest);
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

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> retrieveAll(){
        List<ProductResponseDto> products = this.productService.getAllProducts();
        return ResponseEntity.ok(products);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){
        try {
            this.productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()
                    )
            );
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveOne(
            @PathVariable Long id
    ){
        try{
            return ResponseEntity.ok(this.productService.getProductById(id));
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            e.getMessage()
                    )
            );
        }
    }
}
