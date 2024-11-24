package edu.icet.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.entity.CartEntity;
import edu.icet.crm.entity.OrderEntity;
import edu.icet.crm.entity.OrderItem;
import edu.icet.crm.exception.CustomException;
import edu.icet.crm.repository.CartRepository;
import edu.icet.crm.repository.OrderRepository;
import edu.icet.crm.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public OrderEntity placeOrder(String email, String shippingAddress, String paymentInformation) {
        // Get all items from the cart
        List<CartEntity> cartItems = cartRepository.findByEmail(email);

        if (cartItems.isEmpty()) {
            throw new CustomException("Cart is empty. Cannot place order.");
        }

        // Calculate total cost
        double totalCost = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Create order items from cart items
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(item.getProductId());
                    orderItem.setProductName(item.getProductName());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(item.getPrice());
                    return orderItem;
                })
                .collect(Collectors.toList());

        // Create the order
        OrderEntity order = new OrderEntity();
        order.setEmail(email);
        order.setShippingAddress(shippingAddress);
        order.setPaymentInformation(paymentInformation);
        order.setTotalCost(totalCost);

        // Properly link each OrderItem to the OrderEntity
        orderItems.forEach(order::addItem);

        // Save the order (this will also save associated OrderItems due to cascading)
        OrderEntity savedOrder = orderRepository.save(order);

        // Clear the cart by deleting the cart items
        cartRepository.deleteByEmail(email);

        return savedOrder;
    }

    @Override
    public List<OrderEntity> getOrderHistory(String email) {
        return orderRepository.findByEmail(email);
    }
}

