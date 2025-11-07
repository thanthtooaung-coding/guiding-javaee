package com.springboot.productmanagement.service;

import com.springboot.productmanagement.dto.ProductRequestDto;
import com.springboot.productmanagement.dto.ProductResponseDto;
import com.springboot.productmanagement.model.Product;
import com.springboot.productmanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequestDto product);
    void updateProduct(Long id, ProductRequestDto product);
    void deleteProduct(Long id);


    List<ProductResponseDto> getAllProducts();
    ProductResponseDto getProductById(Long id);
}
