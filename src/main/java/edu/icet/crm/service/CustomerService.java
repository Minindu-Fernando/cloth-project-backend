package edu.icet.crm.service;

import edu.icet.crm.model.Customer;

public interface CustomerService {
    Customer presist(Customer customer);

    void deleteCustomer(Integer id);

    Customer login(String email, String password);
}
