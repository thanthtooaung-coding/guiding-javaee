package org.vinn.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.vinn.mapper.ProductRowMapper;
import org.vinn.model.Product;

import javax.sql.DataSource;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final ProductRowMapper MAPPER = new ProductRowMapper();

    public ProductDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";

        // sql, parameter
        this.jdbcTemplate.update(
                sql,
                product.getName(), product.getPrice()
        );
    }

    @Override
    public Product findById(int id) {
        String sql = "SELECT id, name, price FROM products WHERE id = ?";

        // sql, MAPPER, parameter
        return this.jdbcTemplate.queryForObject(
                sql,
                MAPPER,
                id
        );
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, price FROM products";

        // sql, MAPPER
        return this.jdbcTemplate.query(
                sql,
                MAPPER
        );
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ? WHERE id = ?";

        // sql, parameter
        this.jdbcTemplate.update(
                sql,
                product.getName(), product.getPrice(), product.getId()
        );
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        // sql, parameter
        this.jdbcTemplate.update(
                sql,
                id
        );
    }


}
