package org.ui.main.services.advert.dto;

import java.util.List;

public interface AdvertInfoResponse {
    Long getId();
    String getDescription();
    String getFinalUrl();
    String getTypeRealty();
    String getSellerId();
    String getCity();
    String getDistrict();
    String getAddress();
    String getParserOwnerName();
    String getTypeOwner();
    Long getBuildIdMapTiler();
    Float getLongitude();
    Float getLatitude();
    Long getPropertyId();
    Float getSquare();
    Integer getFloor();
    Integer getRoom();
    Integer getRealtyPrice();
    Integer getTotalPrice();
    String getWithKids();
    String getWithPets();
    List<String> getImages();
    List<String> getFeatureValueUa();

}
