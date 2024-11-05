package edu.icet.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.entity.NewArrivalEntity;
import edu.icet.crm.model.NewArrival;
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

@RequiredArgsConstructor
@Service
public class NewArrivalServiceImpl implements NewArrivalService {
    private final ObjectMapper mapper;
    private final NewArrivalRepository newArrivalRepository;

    @Override
    public NewArrival presistNewArrival(String newArrivalProductJson, MultipartFile image)  throws IOException{
        NewArrival product = mapper.readValue(newArrivalProductJson, NewArrival.class);

        // Save image to the file system
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String imageDirectory = "src/main/resources/image/";
        Path imagePath = Paths.get(imageDirectory + fileName);
        Files.copy(image.getInputStream(), imagePath);

        // Set image path in Product
        product.setImage("http://localhost:8080/images/" + fileName);

        // Save ProductEntity to the database
        NewArrivalEntity savedEntity = newArrivalRepository.save(mapper.convertValue(product, NewArrivalEntity.class));
        return mapper.convertValue(savedEntity, NewArrival.class);    }

    @Override
    public List<NewArrival> getAllProducts() {
        List<NewArrivalEntity> productEntities = (List<NewArrivalEntity>) newArrivalRepository.findAll();
        return productEntities.stream()
                .map(entity -> mapper.convertValue(entity, NewArrival.class))
                .toList();    }
}
