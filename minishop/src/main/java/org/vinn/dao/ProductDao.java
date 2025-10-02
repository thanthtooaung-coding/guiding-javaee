package org.vinn.dao;

import org.vinn.model.Product;

import java.util.List;

public interface ProductDao {
    void save(Product product)throws Exception;
    List<Product> findAll()throws Exception;
    Product findById(Long id) throws Exception;
    void update(Product product) throws Exception;
    void delete(Long id) throws Exception;
}
