package org.ui.main.osm.dto;

public record LocationInfoResponse(
        String location,
        float longitude,
        float latitude
) {
}
