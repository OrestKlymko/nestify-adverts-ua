package org.ui.main.input.osm.dto;

public record LocationInfoResponse(
        String location,
        float longitude,
        float latitude
) {
}
