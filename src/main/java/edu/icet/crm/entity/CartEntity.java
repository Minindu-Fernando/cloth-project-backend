package edu.icet.crm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
@Data
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Change to Long or Integer for GenerationType.IDENTITY

    private String email;
    private Integer productId;
    private Integer quantity;
    private String productName;
    private Double price;
    private String image;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now(); // Set the current date and time when the entity is created
    }
}
