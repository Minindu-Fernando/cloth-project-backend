package edu.icet.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.entity.NewArrivalEntity;
import edu.icet.crm.entity.ProductEntity;
import edu.icet.crm.model.NewArrival;
import edu.icet.crm.model.Product;
import edu.icet.crm.repository.NewArrivalRepository;
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

@RequiredArgsConstructor
@Service
public class NewArrivalServiceImpl implements NewArrivalService {
    private final ObjectMapper mapper;
    private final NewArrivalRepository newArrivalRepository;

    @Override
    public NewArrival presist(String newArrivalProductJson, MultipartFile image) throws IOException {
        NewArrival product = mapper.readValue(newArrivalProductJson, NewArrival.class);

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
        NewArrivalEntity savedEntity = newArrivalRepository.save(mapper.convertValue(product, NewArrivalEntity.class));
        return mapper.convertValue(savedEntity, NewArrival.class);
    }

    @Override
    public List<NewArrival> getAllProducts() {
        List<NewArrivalEntity> productEntities = (List<NewArrivalEntity>) newArrivalRepository.findAll();
        return productEntities.stream()
                .map(entity -> mapper.convertValue(entity, NewArrival.class))
                .toList();
    }

    @Override
    public NewArrival getProductById(Integer id) {
        NewArrivalEntity productEntity = newArrivalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return mapper.convertValue(productEntity, NewArrival.class);    }

    @Override
    public List<NewArrival> searchProducts(String keyword, Double minPrice, Double maxPrice) {
        if (minPrice == null && maxPrice == null) {
            // If no price range is provided, search only by the query
            return newArrivalRepository
                    .findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                            keyword, keyword, keyword)
                    .stream()
                    .map(entity -> mapper.convertValue(entity, NewArrival.class))
                    .collect(Collectors.toList());
        } else if (minPrice == null) {
            // If only maxPrice is provided, use 0 as the default minPrice
            minPrice = 0.0;
        } else if (maxPrice == null) {
            // If only minPrice is provided, use a large value as the default maxPrice
            maxPrice = Double.MAX_VALUE;
        }

        // Perform the search with the price range
        return newArrivalRepository
                .findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndPriceBetween(
                        keyword, keyword, keyword, minPrice, maxPrice)
                .stream()
                .map(entity -> mapper.convertValue(entity, NewArrival.class))
                .collect(Collectors.toList());    }

    @Override
    public void deleteProduct(Integer id) {
        newArrivalRepository.deleteById(id);
    }

    @Override
    public NewArrival updateProduct(Integer id, NewArrival updatedProduct, MultipartFile image) throws IOException {
        Optional<NewArrivalEntity> optionalProductEntity = newArrivalRepository.findById(id);

        if (optionalProductEntity.isPresent()) {
            NewArrivalEntity productEntity = optionalProductEntity.get();

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
            NewArrivalEntity savedEntity = newArrivalRepository.save(productEntity);

            // Convert saved entity to Product DTO (assuming mapper is configured)
            return mapper.convertValue(savedEntity, NewArrival.class);
        } else {
            throw new IOException("Product not found with ID: " + id);
        }
    }
}
