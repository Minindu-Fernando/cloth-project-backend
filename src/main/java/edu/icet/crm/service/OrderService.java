package edu.icet.crm.service;

import edu.icet.crm.model.Order;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrderById(Integer id);
    Order updateOrderStatus(Integer id, String status);
}
