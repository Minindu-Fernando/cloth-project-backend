package edu.icet.crm.controller;

import edu.icet.crm.entity.CartEntity;
import edu.icet.crm.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

private final CartService cartService;

    // Add product to cart
    @PostMapping("/add")
    public ResponseEntity<CartEntity> addToCart(@RequestBody CartEntity cartItem) {
        CartEntity addedCartItem = cartService.addToCart(cartItem);
        return ResponseEntity.ok(addedCartItem);
    }

    // Get cart items by email
    @GetMapping("/{email}")
    public ResponseEntity<List<CartEntity>> getCartByEmail(@PathVariable String email) {
        List<CartEntity> cartItems = cartService.getCartByEmail(email);
        return ResponseEntity.ok(cartItems);
    }
}
