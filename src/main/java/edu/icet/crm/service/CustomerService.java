package edu.icet.crm.service;

import edu.icet.crm.model.Customer;

public interface CustomerService {
    Customer persist(Customer customer) throws IllegalArgumentException;

    void deleteCustomer(Integer id);

    Customer login(String email, String password);

    boolean deleteCustomerIfCredentialsMatch(String email, String password);
}