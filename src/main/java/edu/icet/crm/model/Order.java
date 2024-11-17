package edu.icet.crm.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private Integer id;
    private String customerName;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItem> items;
}
