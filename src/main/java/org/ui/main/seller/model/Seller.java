package org.ui.main.seller.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.ui.main.advert.model.Advert;
import org.ui.main.seller.enums.TypeOwner;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "SELLER")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME_OWNER")
    private String nameOwner;
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_OWNER")
    private TypeOwner typeOwner;
    @Column(name = "NUMBER_PHONE")
    private String numberPhone;
    @Column(name = "SELLER_AUTH_ID")
    private String sellerAuthId;


    @OneToMany(mappedBy = "seller")
    @JsonManagedReference
    private Set<Advert> adverts;
    @ManyToOne
    @JoinColumn(name = "AGENCY_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Agency agency;

    public Long getId() {
        return id;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    public TypeOwner getTypeOwner() {
        return typeOwner;
    }

    public void setTypeOwner(TypeOwner typeOwner) {
        this.typeOwner = typeOwner;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getSellerAuthId() {
        return sellerAuthId;
    }

    public void setSellerAuthId(String sellerAuthId) {
        this.sellerAuthId = sellerAuthId;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Set<Advert> getAdverts() {
        return adverts;
    }

    public void setAdverts(Set<Advert> adverts) {
        this.adverts = adverts;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(id, seller.id) && Objects.equals(nameOwner, seller.nameOwner) && typeOwner == seller.typeOwner && Objects.equals(numberPhone, seller.numberPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameOwner, typeOwner, numberPhone);
    }
}
