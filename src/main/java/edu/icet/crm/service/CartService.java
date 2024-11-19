package edu.icet.crm.service;

import edu.icet.crm.entity.CartEntity;

import java.util.List;

public interface CartService {
    CartEntity addToCart(CartEntity cartItem);

    List<CartEntity> getCartByEmail(String email);
}
