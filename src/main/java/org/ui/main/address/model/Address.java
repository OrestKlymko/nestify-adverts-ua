package org.ui.main.address.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.ui.main.advert.model.Advert;

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

    @ManyToOne
    @JoinColumn(name = "DISTRICT_ID", referencedColumnName = "ID")
    private District district;

    @ManyToOne
    @JoinColumn(name = "STREET_ID", referencedColumnName = "ID")
    private Street street;

    @ManyToOne
    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID")
    private City city;

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

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
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
}
