package edu.icet.crm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.model.Customer;
import edu.icet.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CustomerController {
    private final CustomerService customerService;
    private final ObjectMapper objectMapper;

    @PostMapping("/customer")
    Customer presist(@RequestBody Customer customer) {
        return customerService.presist(customer);
    }

    @DeleteMapping("/customer")
    void deleteCustomer(@RequestParam(name = "id") Integer id) {
        customerService.deleteCustomer(id);
    }

}
