package edu.icet.crm.repository;

import edu.icet.crm.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    // Method for searching by keyword in product name, category, or description
    List<ProductEntity> findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String productName, String category, String description);

    // Method for searching by keyword and filtering by price range
    List<ProductEntity> findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndPriceBetween(
            String productName, String category, String description, Double minPrice, Double maxPrice);
}
