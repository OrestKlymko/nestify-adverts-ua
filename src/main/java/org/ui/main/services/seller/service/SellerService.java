package org.ui.main.services.seller.service;


import org.springframework.stereotype.Service;
import org.ui.main.services.advert.dto.SellerCreateRequest;
import org.ui.main.services.seller.dto.SellerResponse;
import org.ui.main.services.seller.model.Seller;
import org.ui.main.services.seller.repository.SellerRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

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

    public List<SellerResponse> findAll() {
        return sellerRepository.findAll().stream().map(seller -> new SellerResponse(seller.getNumberPhone())).toList();
    }
}
