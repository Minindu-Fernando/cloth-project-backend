package edu.icet.crm.model;

import lombok.Data;

@Data
public class Customer {
    private Integer customerId;
    private String customerName;
    private String email;
    private String contact;
    private String city;
    private String password;
    private String postalCode;
    private String address;
}
