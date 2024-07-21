package org.ui.main.search.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CoordinateResponse(
        @JsonProperty("advertId")
        String advertId,
        @JsonProperty("longitude")
        float longitude,
        @JsonProperty("latitude")
        float latitude
) {
}
