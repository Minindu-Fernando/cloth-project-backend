package edu.icet.crm.service;

import edu.icet.crm.model.NewArrival;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NewArrivalService {
    NewArrival presist(String newArrivalProductJson, MultipartFile image) throws IOException;

    List<NewArrival> getAllProducts();
    NewArrival getProductById(Integer id);

    List<NewArrival> searchProducts(String keyword, Double minPrice, Double maxPrice);

    void deleteProduct(Integer id);

    NewArrival updateProduct(Integer id, NewArrival updatedProduct, MultipartFile image) throws IOException;
}

