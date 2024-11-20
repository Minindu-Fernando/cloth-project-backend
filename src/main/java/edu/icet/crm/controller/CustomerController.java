package edu.icet.crm.controller;

import edu.icet.crm.model.Customer;
import edu.icet.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    // Add Customer
    @PostMapping("/add")
    public ResponseEntity<Customer> persist(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.persist(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // Delete Customer by ID
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCustomer(@RequestParam(name = "id") Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    // Login Customer
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Customer customer) {
        Customer loggedInCustomer = customerService.login(customer.getEmail(), customer.getPassword());
        if (loggedInCustomer != null) {
            return ResponseEntity.ok("Login Successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Invalid email or password!");
        }
    }

    // Delete Customer by Credentials
    @DeleteMapping("/delete-customer")
    public ResponseEntity<String> deleteCustomerIfCredentialsMatch(@RequestBody Customer customer) {
        boolean isDeleted = customerService.deleteCustomerIfCredentialsMatch(customer.getEmail(), customer.getPassword());
        if (isDeleted) {
            return ResponseEntity.ok("Customer deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }
}
