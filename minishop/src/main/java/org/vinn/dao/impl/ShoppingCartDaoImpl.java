package org.vinn.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.vinn.dao.ShoppingCartDao;
import org.vinn.model.ShoppingCart;
import org.vinn.model.ShoppingCartData;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShoppingCartDaoImpl implements ShoppingCartDao {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<ShoppingCartData> ROW_MAPPER = new ProductRowMapper();

    public ShoppingCartDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(ShoppingCartData shoppingCart) throws Exception {
        String sql = "INSERT INTO shopping_cart(user_id, product_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                sql,
                shoppingCart.userId(), shoppingCart.productId(), shoppingCart.quantity()
        );
    }

    @Override
    public List<ShoppingCartData> findAllByUserId(Long userId) throws Exception {
        String sql = "SELECT id, user_id AS userId, product_id, quantity FROM shopping_cart WHERE user_id = ?";
        return jdbcTemplate.query(
                sql,
                ROW_MAPPER,
                userId
        );
    }

    @Override
    public void delete(Long userId, Long productId) throws Exception {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(
                sql,
                userId, productId
        );
    }

    @Override
    public boolean existsByUserIdAndProductId(Long userId, Long productId) throws Exception {
        String sql = "SELECT COUNT(*) FROM shopping_cart WHERE user_id = ? AND product_id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                userId, productId
        ) > 0; // If 0 -> false, if > 0 -> true
    }

    @Override
    public ShoppingCartData findOneByUserIdAndProductId(Long userId, Long productId) throws Exception {
        String sql = """
            SELECT 
                id, user_id AS userId, product_id, quantity
            FROM 
                shopping_cart
            WHERE 
                user_id = ? AND product_id = ?
        """;

        return jdbcTemplate.queryForObject(
                sql,
                ROW_MAPPER,
                userId, productId
        );
    }

    @Override
    public void updateQuantity(ShoppingCartData shoppingCart) throws Exception {
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                shoppingCart.quantity(), shoppingCart.id()
        );
    }

    private static class ProductRowMapper implements RowMapper<ShoppingCartData> {

        @Override
        public ShoppingCartData mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ShoppingCartData(
                    rs.getLong("id"),
                    rs.getLong("userId"),
                    rs.getLong("product_id"),
                    rs.getInt("quantity")
            );
        }
    }
}
