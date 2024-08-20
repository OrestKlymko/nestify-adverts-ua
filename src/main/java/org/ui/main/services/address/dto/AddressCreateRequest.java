package org.ui.main.services.address.dto;

public record AddressCreateRequest(
        long buildIdMapTiler,
        String district,
        String street,
        String city,
        int houseNumber
) {
}
