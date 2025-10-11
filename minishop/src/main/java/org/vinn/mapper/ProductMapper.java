package org.vinn.mapper;

import org.vinn.dto.CategoryDto;
import org.vinn.dto.ProductDto;
import org.vinn.model.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getImageUrl(),
                new CategoryDto(
                        product.getCategory().getId(),
                        product.getCategory().getName(),
                        product.getCategory().getCreatedBy()
                )
        );
    }
}
