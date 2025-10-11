package org.vinn.service.impl;

import org.vinn.dao.CategoryDao;
import org.vinn.dao.UserDao;
import org.vinn.dao.impl.CategoryDaoImpl;
import org.vinn.dao.impl.UserDaoImpl;
import org.vinn.dto.CategoryDto;
import org.vinn.mapper.CategoryMapper;
import org.vinn.model.Category;
import org.vinn.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao;
    private UserDao userDao;

    public CategoryServiceImpl() {
        this.categoryDao = new CategoryDaoImpl();
        this.userDao = new UserDaoImpl();
    }

    @Override
    public void create(String name, Long createdBy) throws Exception {
        categoryDao.save(
                new Category().initialize(name, createdBy)
        );
    }

    @Override
    public List<CategoryDto> retrieveAll() throws Exception {
        List<Category> categories = categoryDao.findAll();

        System.out.println("Pass 1");
        List<CategoryDto> categoryDtoList = categories.stream().map(CategoryMapper::toDto).toList();
        System.out.println("Pass 2");
        for (CategoryDto categoryDto : categoryDtoList) {
            System.out.println("Pass 3");
            categoryDto.setCreatedByUsername(
                    userDao.findById(
                        categoryDto.getCreatedBy()
                    )
                    .getUsername()
            );
        }
        return categoryDtoList;
    }

    @Override
    public CategoryDto retrieveOne(Long id) throws Exception {
        Category category = categoryDao.findById(id);
        return CategoryMapper.toDto(category);
    }

    @Override
    public void edit(Long id, String name) throws Exception {
        Category category = categoryDao.findById(id);
        category.setName(name);
        categoryDao.update(
                category
        );
    }

    @Override
    public void delete(Long id) throws Exception {
        categoryDao.delete(id);
    }

}
