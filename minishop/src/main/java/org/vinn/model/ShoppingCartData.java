package org.vinn.model;

public record ShoppingCartData(
        Long id,
        Long userId,
        Long productId,
        Integer quantity
) {}
