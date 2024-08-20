package org.ui.main.services.address.dto;

public record AllLocationResponse(
        Integer id,
        String city,
        String district,
        String street
) {
}
