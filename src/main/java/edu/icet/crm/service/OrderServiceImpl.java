package edu.icet.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.entity.OrderEntity;
import edu.icet.crm.model.Order;
import edu.icet.crm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ObjectMapper mapper;

    @Override
    public Order createOrder(Order order) {
        OrderEntity entity = mapper.convertValue(order, OrderEntity.class);
        OrderEntity savedEntity = orderRepository.save(entity);
        return mapper.convertValue(savedEntity, Order.class);
    }

    @Override
    public Order getOrderById(Integer id) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        return mapper.convertValue(entity, Order.class);
    }

    @Override
    public Order updateOrderStatus(Integer id, String status) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        entity.setStatus(status);
        OrderEntity updatedEntity = orderRepository.save(entity);
        return mapper.convertValue(updatedEntity, Order.class);
    }
}
