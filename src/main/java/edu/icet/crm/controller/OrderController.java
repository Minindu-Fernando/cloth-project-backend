package edu.icet.crm.controller;

import edu.icet.crm.entity.OrderEntity;
import edu.icet.crm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/orders")
public class OrderController {


    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(
            @RequestParam String email,
            @RequestParam String shippingAddress,
            @RequestParam String paymentInformation) {
        try {
            System.out.println("Placing order with: email=" + email + ", address=" + shippingAddress + ", payment=" + paymentInformation);
            orderService.placeOrder(email, shippingAddress, paymentInformation);
            return ResponseEntity.ok("Order placed successfully.");
        } catch (Exception e) {
            System.err.println("Error placing order: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error placing order: " + e.getMessage());
        }
    }


    @GetMapping("/history/{email}")
    public List<OrderEntity> getOrderHistory(@PathVariable String email) {
        return orderService.getOrderHistory(email);
    }
}
