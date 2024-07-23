package org.ui.main.map.dto;

import java.time.LocalDateTime;
import java.util.List;

public interface MapAdvertsResponse {
    Long getAdvertId();
    String getDescription();
    String getDistrict();
    String getAddress();
    Long getBuildIdMapTiler();
    Float getLongitude();
    Float getLatitude();
    Float getSquare();
    Integer getFloor();
    Integer getRoom();
    Integer getPrice();
    List<String> getFeatures();
    List<String> getAdvantages();
    List<String> getImages();
    String getAgencyCatalog();
    LocalDateTime getPublishedAt();
}
