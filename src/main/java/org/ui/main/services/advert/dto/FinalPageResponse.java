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

	public FinalPageResponse(AdvertInfoResponse advertInfoResponse, SellerResponse sellerResponse) {
		this.id = advertInfoResponse.getId();
		this.address = advertInfoResponse.getAddress();
		this.features = advertInfoResponse.getFeatureValueUa();
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
	}

	public FinalPageResponse(AdvertInfoResponse advertInfoResponse) {
		this.id = advertInfoResponse.getId();
		this.address = advertInfoResponse.getAddress();
		this.features = advertInfoResponse.getFeatureValueUa();
		this.description = advertInfoResponse.getDescription();
		this.numberPhone = "";
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
		this.nameOwner = advertInfoResponse.getParserOwnerName();
		this.typeOwner = advertInfoResponse.getTypeOwner();
		this.agencyName = "";
		this.propertyId = advertInfoResponse.getPropertyId();
		this.realtyPrice = advertInfoResponse.getRealtyPrice();
		this.withPets = advertInfoResponse.getWithPets();
		this.withKids = advertInfoResponse.getWithKids();
		this.typeRealty = advertInfoResponse.getTypeRealty();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNumberPhone() {
		return numberPhone;
	}

	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
	}

	public String getFinalUrl() {
		return finalUrl;
	}

	public void setFinalUrl(String finalUrl) {
		this.finalUrl = finalUrl;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public Float getSquare() {
		return square;
	}

	public void setSquare(Float square) {
		this.square = square;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getBuildIdMapTiler() {
		return buildIdMapTiler;
	}

	public void setBuildIdMapTiler(Long buildIdMapTiler) {
		this.buildIdMapTiler = buildIdMapTiler;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getNameOwner() {
		return nameOwner;
	}

	public void setNameOwner(String nameOwner) {
		this.nameOwner = nameOwner;
	}

	public String getTypeOwner() {
		return typeOwner;
	}

	public void setTypeOwner(String typeOwner) {
		this.typeOwner = typeOwner;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public Integer getRealtyPrice() {
		return realtyPrice;
	}

	public void setRealtyPrice(Integer realtyPrice) {
		this.realtyPrice = realtyPrice;
	}

	public String getWithPets() {
		return withPets;
	}

	public void setWithPets(String withPets) {
		this.withPets = withPets;
	}

	public String getWithKids() {
		return withKids;
	}

	public void setWithKids(String withKids) {
		this.withKids = withKids;
	}

	public String getTypeRealty() {
		return typeRealty;
	}

	public void setTypeRealty(String typeRealty) {
		this.typeRealty = typeRealty;
	}


}
