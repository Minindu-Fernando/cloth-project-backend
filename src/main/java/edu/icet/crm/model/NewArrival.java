package edu.icet.crm.model;

import lombok.Data;

@Data
public class NewArrival {
    private String productId;
    private String productName;
    private String description;
    private String category;
    private Integer quantity;
    private Double price;
    private String image;
}
