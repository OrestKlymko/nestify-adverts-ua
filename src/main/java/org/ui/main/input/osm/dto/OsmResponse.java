package org.ui.main.input.osm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OsmResponse(
        @JsonProperty("osm_id")
        Long osmId,
        @JsonProperty("osm_type")
        String osmType,
        Float lat,
        Float lon
) {
}