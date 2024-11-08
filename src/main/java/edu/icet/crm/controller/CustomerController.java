package edu.icet.crm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;


    @PostMapping("/add")
    Customer presist(@RequestBody Customer customer) {
        return customerService.presist(customer);
    }

    @DeleteMapping("/delete")
    void deletedCustomer(@RequestParam(name = "id") Integer id) {
        customerService.deleteCustomer(id);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Customer customer){
        Customer loggedInCustomer = customerService.login(customer.getEmail(), customer.getPassword());
        if (loggedInCustomer != null){
            return ResponseEntity.ok("Login Successfully !");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Invalid email or Password !");
        }
    }
    @DeleteMapping("/delete-customer")
    public ResponseEntity<String> deleteCustomer(@RequestBody Customer customer){
        boolean isDeleted = customerService.deleteCustomerIfCredentialsMatch(customer.getEmail(),customer.getPassword());
        if (isDeleted){
            return ResponseEntity.ok("Customer deleted successfully.");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

}
