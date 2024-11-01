package edu.icet.crm.service;

import edu.icet.crm.model.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CustomerService {
    Customer presist(Customer customer);

    void deleteCustomer(Integer id);

}
