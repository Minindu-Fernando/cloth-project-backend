package edu.icet.crm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.model.Product;
import edu.icet.crm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @PostMapping("/product")
    public Product presist(@RequestParam("product") String productJson,
                           @RequestParam("image") MultipartFile image) throws IOException {
        return productService.presist(productJson, image);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping("/products/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        List<Product> products = productService.searchProducts(keyword, minPrice, maxPrice);

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found for the given search criteria.");
        }

        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/delete-product/{id}")
    public void deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
    }

    @PutMapping("/update-product-by/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Integer id,
            @RequestParam("productJson") String productJson,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            // Convert productJson to Product object
            Product updatedProduct = objectMapper.readValue(productJson, Product.class);

            // Update product using service
            Product updatedProductResult = productService.updateProduct(id, updatedProduct, image);

            return ResponseEntity.ok(updatedProductResult);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }


}
