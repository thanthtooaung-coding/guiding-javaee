package org.vinn.service.impl;

import org.vinn.dao.CategoryDao;
import org.vinn.dao.ProductDao;
import org.vinn.dto.ProductDto;
import org.vinn.mapper.ProductMapper;
import org.vinn.model.Category;
import org.vinn.model.Product;
import org.vinn.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    ProductDao productDao;
    CategoryDao categoryDao;
    @Override
    public void create(ProductDto productDto) throws Exception {
        Category category = categoryDao.findById(
                productDto.getCategory().getId()
        );

        productDao.save(
                new Product().initialize(productDto, category, 1L)
        );
    }

    @Override
    public List<ProductDto> retrieveAll() throws Exception {
        List<Product> products = productDao.findAll();
        return products.stream().map(ProductMapper::toDto).toList();
    }

    @Override
    public void edit(ProductDto productDto) throws Exception {
        Product product = productDao.findById(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        /*product.setCategory(
                productDto.getCa
        );*/
        productDao.update(product);
    }

    @Override
    public void delete(Long id) throws Exception {
        productDao.delete(id);
    }

    @Override
    public ProductDto retrieveOne(Long id) throws Exception {
        Product product = productDao.findById(id);
        return ProductMapper.toDto(product);
    }
}
