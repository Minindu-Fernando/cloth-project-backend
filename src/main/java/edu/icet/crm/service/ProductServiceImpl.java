package edu.icet.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.entity.ProductEntity;
import edu.icet.crm.model.Product;
import edu.icet.crm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper mapper;

    @Override
    public Product presist(String productJson, MultipartFile image) throws IOException {
        Product product = mapper.readValue(productJson, Product.class);

        // Save image to the file system
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String imageDirectory = "src/main/resources/image/";
        Path imagePath = Paths.get(imageDirectory + fileName);

        // Avoid overwriting existing files
        if (Files.exists(imagePath)) {
            throw new IOException("Image with the same name already exists: " + fileName);
        }

        // Copy the image to the file system
        Files.copy(image.getInputStream(), imagePath);

        // Set image URL in Product
        product.setImage("http://localhost:8080/images/" + fileName);

        // Save ProductEntity to the database
        ProductEntity savedEntity = productRepository.save(mapper.convertValue(product, ProductEntity.class));
        return mapper.convertValue(savedEntity, Product.class);
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities = (List<ProductEntity>) productRepository.findAll();
        return productEntities.stream()
                .map(entity -> mapper.convertValue(entity, Product.class))
                .toList();
    }

    @Override
    public Product getProductById(Integer id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return mapper.convertValue(productEntity, Product.class);
    }

    @Override
    public List<Product> searchProducts(String keyword, Double minPrice, Double maxPrice) {
        if (minPrice == null && maxPrice == null) {
            // If no price range is provided, search only by the query
            return productRepository
                    .findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                            keyword, keyword, keyword)
                    .stream()
                    .map(entity -> mapper.convertValue(entity, Product.class))
                    .collect(Collectors.toList());
        } else if (minPrice == null) {
            // If only maxPrice is provided, use 0 as the default minPrice
            minPrice = 0.0;
        } else if (maxPrice == null) {
            // If only minPrice is provided, use a large value as the default maxPrice
            maxPrice = Double.MAX_VALUE;
        }

        // Perform the search with the price range
        return productRepository
                .findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndPriceBetween(
                        keyword, keyword, keyword, minPrice, maxPrice)
                .stream()
                .map(entity -> mapper.convertValue(entity, Product.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Integer id, Product updatedProduct, MultipartFile image) throws IOException {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);

        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();

            // Update basic fields
            productEntity.setProductName(updatedProduct.getProductName());
            productEntity.setDescription(updatedProduct.getDescription());
            productEntity.setCategory(updatedProduct.getCategory());
            productEntity.setQuantity(updatedProduct.getQuantity());
            productEntity.setPrice(updatedProduct.getPrice());

            // Handle image if a new one is provided
            if (image != null && !image.isEmpty()) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
                String imageDirectory = "src/main/resources/image/";
                Path imagePath = Paths.get(imageDirectory + fileName);

                // Copy the new image to the directory, overwriting the old one if necessary
                Files.copy(image.getInputStream(), imagePath);

                // Set the new image URL
                productEntity.setImage("http://localhost:8080/images/" + fileName);
            }

            // Save updated entity
            ProductEntity savedEntity = productRepository.save(productEntity);

            // Convert saved entity to Product DTO (assuming mapper is configured)
            return mapper.convertValue(savedEntity, Product.class);
        } else {
            throw new IOException("Product not found with ID: " + id);
        }
    }
}



