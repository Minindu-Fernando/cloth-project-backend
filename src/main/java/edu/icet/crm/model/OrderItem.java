package edu.icet.crm.model;

import lombok.Data;

@Data
public class OrderItem {
    private Integer productId;
    private Integer quantity;
    private Double price;
}
