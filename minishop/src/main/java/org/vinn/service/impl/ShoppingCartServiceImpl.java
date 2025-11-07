package org.vinn.service.impl;

import org.vinn.config.DatabaseConfig;
import org.vinn.dao.ShoppingCartDao;
import org.vinn.dao.impl.ShoppingCartDaoImpl;
import org.vinn.model.ShoppingCartData;
import org.vinn.service.ShoppingCartService;

import java.util.List;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartDao shoppingCartDao;

    public ShoppingCartServiceImpl(ShoppingCartDao shoppingCartDao) {
        this.shoppingCartDao = new ShoppingCartDaoImpl(DatabaseConfig.getDataSource());
    }

    @Override
    public void add(Long userId, Long productId, Integer quantity) throws Exception {
        if(shoppingCartDao.existsByUserIdAndProductId(userId, productId)) {
            ShoppingCartData shoppingCartData = shoppingCartDao.findOneByUserIdAndProductId(userId, productId);
            shoppingCartDao.updateQuantity(
                    new ShoppingCartData(
                            shoppingCartData.id(),
                            userId,
                            productId,
                            shoppingCartData.quantity() + quantity
                    )
            );
        } else {
            shoppingCartDao.save(
                    new ShoppingCartData(0L, userId, productId, quantity)
            );
        }
    }

    @Override
    public List<ShoppingCartData> retrieveAll(Long userId) throws Exception {
        return shoppingCartDao.findAllByUserId(userId);
    }

    @Override
    public void remove(Long userId, Long productId) throws Exception {
        shoppingCartDao.delete(userId, productId);
    }
}
