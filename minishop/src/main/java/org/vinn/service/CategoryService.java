package org.vinn.service;

import org.vinn.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    void create(String name, Long createdBy) throws Exception;
    List<CategoryDto> retrieveAll() throws Exception;
    CategoryDto retrieveOne(Long id) throws Exception;
    void edit(Long id, String name) throws Exception;
    void delete(Long id) throws Exception;
}
