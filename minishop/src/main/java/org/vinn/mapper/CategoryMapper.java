package org.vinn.mapper;

import org.vinn.dto.CategoryDto;
import org.vinn.model.Category;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}
