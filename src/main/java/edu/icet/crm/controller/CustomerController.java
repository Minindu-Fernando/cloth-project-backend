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

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Customer customer){
        Customer loggedInCustomer = customerService.login(customer.getEmail(), customer.getPassword());
        if (loggedInCustomer != null){
            return ResponseEntity.ok("Login Successfully !");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Invalid email or Password !");
        }
    }

}
