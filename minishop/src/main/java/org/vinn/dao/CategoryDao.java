package org.vinn.dao;

import org.vinn.model.Category;

import java.util.List;

public interface CategoryDao {
    void save(Category category) throws Exception;
    List<Category> findAll() throws Exception;
    void delete(Long id) throws Exception;
}
