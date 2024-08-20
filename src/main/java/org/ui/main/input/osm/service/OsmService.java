package org.ui.main.input.osm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ui.main.input.osm.dto.LocationInfoResponse;
import org.ui.main.input.osm.dto.OsmResponse;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class OsmService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public OsmService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    public LocationInfoResponse getCoordinatesFromOSM(String location) {
        String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";
        String decodeLocation = URLDecoder.decode(location, StandardCharsets.UTF_8);
        String query = String.format("%s?q=%s&format=json",
                NOMINATIM_API_URL, decodeLocation.trim());
        String response = restTemplate.getForObject(query, String.class);
        try {
            OsmResponse[] osmResponse = objectMapper.readValue(response, OsmResponse[].class);

            if (osmResponse != null && osmResponse.length > 0) {
                return new LocationInfoResponse(
                        decodeLocation,
                        osmResponse[0].lon(), osmResponse[0].lat()
                );
            }
        } catch (JsonProcessingException ignored) {
            return null;
        }
        return null;
    }
}

