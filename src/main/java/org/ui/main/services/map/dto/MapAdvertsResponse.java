package org.ui.main.services.map.dto;

import java.time.LocalDateTime;
import java.util.List;

public interface MapAdvertsResponse {
    Long getId();
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
    String getAdvertImage();
    String getAgencyCatalog();
    LocalDateTime getPublishedAt();
}
