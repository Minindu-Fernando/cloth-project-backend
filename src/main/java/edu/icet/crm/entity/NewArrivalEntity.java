package edu.icet.crm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "new_arrival")
public class NewArrivalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private String description;
    private String category;
    private Integer quantity;
    private Double price;
    private String image;
}
