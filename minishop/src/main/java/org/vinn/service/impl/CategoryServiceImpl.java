package org.vinn.service.impl;

import org.vinn.dao.CategoryDao;
import org.vinn.dao.impl.CategoryDaoImpl;
import org.vinn.dto.CategoryDto;
import org.vinn.mapper.CategoryMapper;
import org.vinn.model.Category;
import org.vinn.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao;

    public CategoryServiceImpl() {
        this.categoryDao = new CategoryDaoImpl();
    }

    @Override
    public void create(String name) throws Exception {
        categoryDao.save(
                new Category().initialize(name)
        );
    }

    @Override
    public List<CategoryDto> retrieveAll() throws Exception {
        List<Category> categories = categoryDao.findAll();
        /*
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : categories) {
            categoryDtoList.add(CategoryMapper.toDto(category));
        }*/
        return categories.stream().map(CategoryMapper::toDto).toList();
    }

    @Override
    public void edit(Long id, String name) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {
        categoryDao.delete(id);
    }

}
