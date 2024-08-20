package org.ui.main.services.address.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "DISTRICTS")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DISTRICT_NAME")
    private String districtName;
    @ManyToOne
    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID")
    private City city;
    @OneToMany(mappedBy = "district")
    private List<Address> addresses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
