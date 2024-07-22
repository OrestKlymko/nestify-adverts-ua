package org.ui.main.advert.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.ui.main.advert.model.enums.AllowedStatus;

import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "PROPERTY_BUILDING")
public class PropertyRealty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long propertyId;
    @Column(name = "SQUARE")
    private float square;
    @Column(name = "FLOOR")
    private int floor;
    @Column(name = "ROOM")
    private int room;
    @Column(name = "REALTY_PRICE")
    private int realtyPrice;
    @Column(name = "ENERGY_PRICE")
    private int energyPrice;
    @Column(name = "TOTAL_PRICE")
    private int totalPrice;
    @OneToMany(mappedBy = "propertyRealty")
    private Set<Features> features;
    @Enumerated(EnumType.STRING)
    @Column(name = "WITH_PETS")
    private AllowedStatus withPets;
    @Enumerated(EnumType.STRING)
    @Column(name = "WITH_KIDS")
    private AllowedStatus withKids;
    @OneToMany(mappedBy = "propertyRealty")
    @JsonManagedReference
    private Set<Advantages> advantageList;
    @OneToOne(mappedBy = "propertyRealty")
    @JsonManagedReference
    private Advert advert;


    public float getSquare() {
        return square;
    }


    public int getFloor() {
        return floor;
    }


    public int getRoom() {
        return room;
    }


    public int getTotalPrice() {
        return totalPrice;
    }


    public Set<Features> getFeatures() {
        return features;
    }


    public Set<Advantages> getAdvantageList() {
        return advantageList;
    }


    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyRealty that = (PropertyRealty) o;
        return Float.compare(square, that.square) == 0 && floor == that.floor && room == that.room && realtyPrice == that.realtyPrice && energyPrice == that.energyPrice && totalPrice == that.totalPrice && Objects.equals(propertyId, that.propertyId) && Objects.equals(features, that.features) && withPets == that.withPets && withKids == that.withKids && Objects.equals(advantageList, that.advantageList) && Objects.equals(advert, that.advert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyId, square, floor, room, realtyPrice, energyPrice, totalPrice, features, withPets, withKids, advantageList, advert);
    }
}
