package org.ui.main.seller.service;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.ui.main.advert.dto.SellerCreateRequest;
import org.ui.main.seller.model.Seller;
import org.ui.main.seller.repository.SellerRepository;

import java.util.HashSet;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @PostMapping
    public void createSeller(SellerCreateRequest sellerCreateRequest) {
        Seller seller = new Seller();
        seller.setSellerAuthId(sellerCreateRequest.sellerAuthId());
        seller.setNameOwner(sellerCreateRequest.nameOwner());
        seller.setAgency(null);
        seller.setTypeOwner(sellerCreateRequest.typeOwner());
        seller.setNumberPhone(seller.getNumberPhone());
        seller.setAdverts(new HashSet<>());
        sellerRepository.save(seller);
    }

    public void findAll() {
    }
}
