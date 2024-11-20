package edu.icet.crm.repository;

import edu.icet.crm.entity.NewArrivalEntity;
import edu.icet.crm.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewArrivalRepository extends CrudRepository<NewArrivalEntity,Integer> {
    List<NewArrivalEntity> findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String productName, String category, String description);

    // Method for searching by keyword and filtering by price range
    List<NewArrivalEntity> findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndPriceBetween(
            String productName, String category, String description, Double minPrice, Double maxPrice);
}
