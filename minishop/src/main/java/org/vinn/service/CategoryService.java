package org.vinn.service;

import org.vinn.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    void create(String name) throws Exception;
    List<CategoryDto> retrieveAll() throws Exception;
    void edit(Long id, String name) throws Exception;
    void delete(Long id) throws Exception;
}
