package edu.icet.crm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.model.NewArrival;
import edu.icet.crm.model.Product;
import edu.icet.crm.service.NewArrivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/arrival")
@CrossOrigin
public class NewArrivalController {

    private final NewArrivalService newArrivalService;
    private final ObjectMapper objectMapper;
    @PostMapping("/product")
    public NewArrival presist(@RequestParam("product") String productJson,
                           @RequestParam("image") MultipartFile image) throws IOException {
        return newArrivalService.presist(productJson, image);
    }

    @GetMapping("/products")
    public List<NewArrival> getAllProducts() {
        return newArrivalService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public NewArrival getProductById(@PathVariable Integer id) {
        return newArrivalService.getProductById(id);
    }

    @GetMapping("/products/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        List<NewArrival> products = newArrivalService.searchProducts(keyword, minPrice, maxPrice);

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found for the given search criteria.");
        }

        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/delete-product/{id}")
    public void deleteProduct(@PathVariable Integer id){
        newArrivalService.deleteProduct(id);
    }

    @PutMapping("/update-product-by/{id}")
    public ResponseEntity<NewArrival> updateProduct(
            @PathVariable Integer id,
            @RequestParam("productJson") String productJson,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            // Convert productJson to Product object
            NewArrival updatedProduct = objectMapper.readValue(productJson, NewArrival.class);

            // Update product using service
            NewArrival updatedProductResult = newArrivalService.updateProduct(id, updatedProduct, image);

            return ResponseEntity.ok(updatedProductResult);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
