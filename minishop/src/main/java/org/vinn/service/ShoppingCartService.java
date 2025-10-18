package org.vinn.service;

import org.vinn.model.ShoppingCartData;

import java.util.List;

public interface ShoppingCartService {
    void add(Long userId, Long productId, Integer quantity) throws Exception;
    List<ShoppingCartData> retrieveAll(Long userId) throws Exception;
    void remove(Long userId, Long productId) throws Exception;
}
