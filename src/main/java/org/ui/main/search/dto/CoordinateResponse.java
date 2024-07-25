package org.ui.main.search.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CoordinateResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("longitude")
        double longitude,
        @JsonProperty("latitude")
        double latitude
) {
}
