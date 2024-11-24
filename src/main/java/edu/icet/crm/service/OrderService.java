package edu.icet.crm.service;

import edu.icet.crm.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderEntity placeOrder(String email, String shippingAddress, String paymentInformation);
    List<OrderEntity> getOrderHistory(String email);
}
