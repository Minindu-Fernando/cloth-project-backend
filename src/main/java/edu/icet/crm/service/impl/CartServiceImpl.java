package edu.icet.crm.service.impl;

import edu.icet.crm.entity.CartEntity;
import edu.icet.crm.repository.CartRepository;
import edu.icet.crm.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Override
    public CartEntity addToCart(CartEntity cartItem) {
        return cartRepository.save(cartItem);
    }

    @Override
    public List<CartEntity> getCartByEmail(String email) {
        return cartRepository.findByEmail(email);
    }
    @Override
    public void removeCartItem(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new IllegalArgumentException("Cart item not found");
        }
        cartRepository.deleteById(id); // Delete the item
    }
    @Override
    public CartEntity updateCartItem(Long id, Integer quantity) {
        CartEntity cartItem = cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        cartItem.setQuantity(quantity);
        return cartRepository.save(cartItem);
    }

}

