package org.ui.main.advert.dto;

public record AllLocationResponse(
        Integer id,
        String city,
        String district,
        String street
) {
}
