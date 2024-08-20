package org.ui.main.services.seller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.ui.main.services.advert.dto.SellerCreateRequest;
import org.ui.main.services.seller.dto.SellerResponse;
import org.ui.main.services.seller.service.SellerService;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
@Tag(name = "Seller")
public class SellerController {
    private final SellerService sellerService;

    public SellerController(SellerService service) {
        this.sellerService = service;
    }

    @PostMapping
    public void createSeller(@RequestBody SellerCreateRequest request) {
        sellerService.createSeller(request);
    }

    @GetMapping
    public List<SellerResponse> getAllSellers() {
        return sellerService.findAll();
    }
}
