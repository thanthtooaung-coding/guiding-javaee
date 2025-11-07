package com.springboot.productmanagement.mapper;

import com.springboot.productmanagement.dto.ProductResponseDto;
import com.springboot.productmanagement.model.Product;

public class ProductMapper {
    public static ProductResponseDto toProductResponseDto(Product product){
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getShortDescription(),
                product.getLongDescription(),
                product.getPrice()
        );
    }
}
