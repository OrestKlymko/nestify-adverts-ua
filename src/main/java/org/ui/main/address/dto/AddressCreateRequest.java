package org.ui.main.address.dto;

public record AddressCreateRequest(
        long buildIdMapTiler,
        String district,
        String street,
        String city,
        int houseNumber,
        double latitude,
        double longitude
) {
}
