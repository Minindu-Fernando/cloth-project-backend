package edu.icet.crm.service;

import edu.icet.crm.model.NewArrival;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NewArrivalService {
    NewArrival presistNewArrival(String newArrivalProductJson, MultipartFile image) throws IOException;

    List<NewArrival> getAllProducts();
}
