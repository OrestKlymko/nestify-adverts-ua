package org.ui.main.address.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ui.main.address.dto.AllLocationResponse;
import org.ui.main.address.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<AllLocationResponse> allLocation() {
        return addressService.getAllLocation();
    }
}
