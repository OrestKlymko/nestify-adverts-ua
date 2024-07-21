package org.ui.main.advert.dto;

import java.util.List;

public interface FinalPageResponse {
    Long getId();
    String getDescription();
    String getFinalUrl();
    String getTypeRealty();
    String getNameOwner();
    String getTypeOwner();
    String getNumberPhone();
    String getCity();
    String getDistrict();
    String getAddress();
    Long getBuildIdMapTiler();
    Float getLongitude();
    Float getLatitude();
    String getAgencyName();
    Long getPropertyId();
    Float getSquare();
    Integer getFloor();
    Integer getRoom();
    Integer getRealtyPrice();
    Integer getTotalPrice();
    String getWithKids();
    String getWithPets();
    List<String> getImages();
    List<String> getFeatures();
    List<String> getAdvantages();
}
