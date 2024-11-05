package edu.icet.crm.controller;

import edu.icet.crm.model.NewArrival;
import edu.icet.crm.service.NewArrivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class NewArrivalController {

    private final NewArrivalService newArrivalService;
    @PostMapping("/product-new-arrival")
    public NewArrival presistNewArrival(@RequestParam("product") String newArrivalProductJson,
                                        @RequestParam("image") MultipartFile image) throws IOException {
        return newArrivalService.presistNewArrival(newArrivalProductJson,image);
    }

    @GetMapping("/new-arrival-products")
    public List<NewArrival> getAllProducts() {
        return newArrivalService.getAllProducts();
    }
}
