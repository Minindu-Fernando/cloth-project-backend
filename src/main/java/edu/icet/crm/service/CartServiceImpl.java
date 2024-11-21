package edu.icet.crm.service;

import edu.icet.crm.entity.CartEntity;
import edu.icet.crm.repository.CartRepository;
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
    public CartEntity updateCartItem(Long id, Integer newQuantity) {
        CartEntity cartItem = cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        cartItem.setQuantity(newQuantity);
        return cartRepository.save(cartItem); // Save the updated item
    }

    @Override
    public void removeCartItem(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new IllegalArgumentException("Cart item not found");
        }
        cartRepository.deleteById(id); // Delete the item
    }
}

