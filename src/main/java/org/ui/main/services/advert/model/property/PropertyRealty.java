package org.ui.main.services.advert.model.property;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.ui.main.services.advert.model.Advert;
import org.ui.main.services.advert.model.enums.AllowedStatus;

import java.util.List;
import java.util.Objects;


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
    @ManyToMany
    @JoinTable(name = "PROPERTY_FEATURES",
            joinColumns = @JoinColumn(name = "PROPERTY_ID"),
            inverseJoinColumns = @JoinColumn(name = "FEATURE_ID"))
    private List<Features> features;
    @Enumerated(EnumType.STRING)
    @Column(name = "WITH_PETS")
    private AllowedStatus withPets;
    @Column(name = "MAX_PEOPLE")
    private int maxPeople;
    @Enumerated(EnumType.STRING)
    @Column(name = "WITH_KIDS")
    private AllowedStatus withKids;
    @ManyToMany
    @JoinTable(name = "PROPERTY_ADVANTAGES",
    joinColumns = @JoinColumn(name = "PROPERTY_ID"),
    inverseJoinColumns = @JoinColumn(name = "ADVANTAGE_ID"))
    private List<Advantages> advantageList;
    @OneToOne(mappedBy = "propertyRealty")
    @JsonManagedReference
    private Advert advert;


    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public float getSquare() {
        return square;
    }

    public void setSquare(float square) {
        this.square = square;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getRealtyPrice() {
        return realtyPrice;
    }

    public void setRealtyPrice(int realtyPrice) {
        this.realtyPrice = realtyPrice;
    }

    public int getEnergyPrice() {
        return energyPrice;
    }

    public void setEnergyPrice(int energyPrice) {
        this.energyPrice = energyPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Features> getFeatures() {
        return features;
    }

    public void setFeatures(List<Features> features) {
        this.features = features;
    }

    public AllowedStatus getWithPets() {
        return withPets;
    }

    public void setWithPets(AllowedStatus withPets) {
        this.withPets = withPets;
    }

    public AllowedStatus getWithKids() {
        return withKids;
    }

    public void setWithKids(AllowedStatus withKids) {
        this.withKids = withKids;
    }

    public List<Advantages> getAdvantageList() {
        return advantageList;
    }

    public void setAdvantageList(List<Advantages> advantageList) {
        this.advantageList = advantageList;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyRealty that = (PropertyRealty) o;
        return Float.compare(square, that.square) == 0 && floor == that.floor && room == that.room && realtyPrice == that.realtyPrice && energyPrice == that.energyPrice && totalPrice == that.totalPrice && Objects.equals(propertyId, that.propertyId) && withPets == that.withPets && withKids == that.withKids;
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyId, square, floor, room, realtyPrice, energyPrice, totalPrice, withPets, withKids);
    }
}
