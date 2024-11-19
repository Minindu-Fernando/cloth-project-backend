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
        // Save the cart item in the database
        return cartRepository.save(cartItem);
    }

    @Override
    public List<CartEntity> getCartByEmail(String email) {
        // Retrieve all cart items for the given email
        return cartRepository.findByEmail(email);
    }
}
