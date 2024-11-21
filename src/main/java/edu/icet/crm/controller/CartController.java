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

    @PostMapping("/add")
    public ResponseEntity<CartEntity> addToCart(@RequestBody CartEntity cartItem) {
        CartEntity addedCartItem = cartService.addToCart(cartItem);
        return ResponseEntity.ok(addedCartItem);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<CartEntity>> getCartByEmail(@PathVariable String email) {
        List<CartEntity> cartItems = cartService.getCartByEmail(email);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartEntity> updateCartItem(@PathVariable Long id, @RequestParam Integer quantity) {
        CartEntity updatedCartItem = cartService.updateCartItem(id, quantity);
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
        return ResponseEntity.noContent().build();
    }
}
