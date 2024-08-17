package org.ui.main.seller;


import org.springframework.web.bind.annotation.*;
import org.ui.main.advert.dto.SellerCreateRequest;
import org.ui.main.seller.dto.SellerResponse;
import org.ui.main.seller.service.SellerService;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
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
