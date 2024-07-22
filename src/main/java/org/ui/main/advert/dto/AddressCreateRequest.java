package org.ui.main.advert.dto;

public record AddressCreateRequest(
        long buildIdMapTiler,
        String district,
        String addressName,
        String city,
        double latitude,
        double longitude
) {
}
