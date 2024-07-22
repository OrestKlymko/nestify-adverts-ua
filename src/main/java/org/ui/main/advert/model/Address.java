package org.ui.main.advert.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ADDRESS")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "BUILD_MAP_TILER")
	private long buildIdMapTiler;
	@Column(name = "DISTRICT")
	private String district;
	@Column(name = "ADDRESS_NAME")
	private String addressName;
	@Column(name = "CITY")
	private String city;
	@Column(name = "LATITUDE")
	private double latitude;
	@Column(name = "LONGITUDE")
	private double longitude;
	@OneToMany(mappedBy = "address")
	@JsonManagedReference
	private Set<Advert> adverts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getBuildIdMapTiler() {
		return buildIdMapTiler;
	}

	public void setBuildIdMapTiler(long buildIdMapTiler) {
		this.buildIdMapTiler = buildIdMapTiler;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Set<Advert> getAdverts() {
		return adverts;
	}

	public void setAdverts(Set<Advert> adverts) {
		this.adverts = adverts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return buildIdMapTiler == address.buildIdMapTiler && Double.compare(latitude, address.latitude) == 0 && Double.compare(longitude, address.longitude) == 0 && Objects.equals(id, address.id) && Objects.equals(district, address.district) && Objects.equals(addressName, address.addressName) && Objects.equals(city, address.city);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, buildIdMapTiler, district, addressName, city, latitude, longitude);
	}
}
