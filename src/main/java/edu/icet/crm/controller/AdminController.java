package edu.icet.crm.controller;

import edu.icet.crm.model.Admin;
import edu.icet.crm.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // Add Admin
    @PostMapping("/add")
    public ResponseEntity<Admin> persist(@RequestBody Admin admin) {
        try {
            Admin savedAdmin = adminService.persist(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmin);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // Admin Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        Admin admin = adminService.login(email, password);
        if (admin != null) {
            return ResponseEntity.ok("Login Successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!");
        }
    }
}
