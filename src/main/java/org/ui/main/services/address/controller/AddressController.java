package org.ui.main.services.address.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ui.main.services.address.dto.AllLocationResponse;
import org.ui.main.services.address.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@Tag(name = "Address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    @Operation(summary = "Get all location available in database")
    public List<AllLocationResponse> allLocation() {
        return addressService.getAllLocation();
    }
}
