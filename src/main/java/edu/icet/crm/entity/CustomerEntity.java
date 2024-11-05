package edu.icet.crm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    private String customerName;
    private String email;
    private String contact;
    private String city;
    private String password;
    private String postalCode;
    private String address;
}
