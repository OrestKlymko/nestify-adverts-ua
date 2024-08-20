package org.ui.main.services.map.dto;

public interface PointMapResponse {
    Long getAddressHouseId();
    Long getBuildIdMapTiler();
    Float getLongitude();
    Float getLatitude();
    Integer getLowerPrice();
}
