package edu.icet.crm.model;

import lombok.Data;

@Data
public class Admin {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
}
