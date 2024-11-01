package edu.icet.crm.controller;

import edu.icet.crm.model.Product;
import edu.icet.crm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    public Product presist(@RequestParam("product") String productJson,
                           @RequestParam("image") MultipartFile image) throws IOException {
        return productService.presist(productJson, image);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
