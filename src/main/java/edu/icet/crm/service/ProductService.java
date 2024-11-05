package edu.icet.crm.service;

import edu.icet.crm.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product presist(String productJson, MultipartFile image) throws IOException;
    List<Product> getAllProducts();

    Product getProductById(Integer id);
}
