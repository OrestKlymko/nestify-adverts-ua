package org.ui.main.advert.dto;

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
