package org.ui.main.services.advert.dto;

import java.util.List;

public class FinalPageResponse {
    private Long id;
    private String address;
    private List<String> features;
    private String description;
    private String numberPhone;
    private String finalUrl;
    private String city;
    private Float longitude;
    private Float latitude;
    private Integer totalPrice;
    private Integer room;
    private Float square;
    private Integer floor;
    private String district;
    private Long buildIdMapTiler;
    private List<String> images;
    private String nameOwner;
    private String typeOwner;
    private String agencyName;
    private Long propertyId;
    private Integer realtyPrice;
    private String withPets;
    private String withKids;
    private String typeRealty;
    private List<String> advantages;

    public FinalPageResponse(AdvertInfoResponse advertInfoResponse, SellerResponse sellerResponse) {
        this.id = advertInfoResponse.getId();
        this.address = advertInfoResponse.getAddress();
        this.features = advertInfoResponse.getFeatures();
        this.description = advertInfoResponse.getDescription();
        this.numberPhone = sellerResponse.phoneNumber();
        this.finalUrl = advertInfoResponse.getFinalUrl();
        this.city = advertInfoResponse.getCity();
        this.longitude = advertInfoResponse.getLongitude();
        this.latitude = advertInfoResponse.getLatitude();
        this.totalPrice = advertInfoResponse.getTotalPrice();
        this.room = advertInfoResponse.getRoom();
        this.square = advertInfoResponse.getSquare();
        this.floor = advertInfoResponse.getFloor();
        this.district = advertInfoResponse.getDistrict();
        this.buildIdMapTiler = advertInfoResponse.getBuildIdMapTiler();
        this.images = advertInfoResponse.getImages();
        this.nameOwner = sellerResponse.firstName();
        this.typeOwner = sellerResponse.agencyName() == null ? "OWNER" : "AGENCY";
        this.agencyName = sellerResponse.agencyName();
        this.propertyId = advertInfoResponse.getPropertyId();
        this.realtyPrice = advertInfoResponse.getRealtyPrice();
        this.withPets = advertInfoResponse.getWithPets();
        this.withKids = advertInfoResponse.getWithKids();
        this.typeRealty = advertInfoResponse.getTypeRealty();
        this.advantages = advertInfoResponse.getAdvantages();
    }
}
