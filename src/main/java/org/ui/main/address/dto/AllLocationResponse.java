package org.ui.main.address.dto;

public record AllLocationResponse(
        Integer id,
        String city,
        String district,
        String street
) {
}
