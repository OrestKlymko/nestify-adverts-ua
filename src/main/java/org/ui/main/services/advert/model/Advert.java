package org.ui.main.services.advert.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.ui.main.services.address.model.Address;
import org.ui.main.services.advert.model.enums.Status;
import org.ui.main.services.advert.model.enums.TypeRealty;
import org.ui.main.services.advert.model.property.PropertyRealty;
import org.ui.main.services.seller.model.Seller;
import org.ui.main.services.review.model.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ADVERTS")
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_REALTY")
    private TypeRealty typeRealty;
    @Column(name = "PUBLISHED_AT")
    private LocalDateTime publishedAt;
    @Column(name = "EDITED_AT")
    private LocalDateTime editedAt;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "FINAL_URL")
    private String finalUrl;
    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "IMAGES", joinColumns = @JoinColumn(name = "ADVERT_ID"))
    @Column(name = "IMAGE_URL")
    private Set<String> images;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROPERTY_ID", referencedColumnName = "ID")
    @JsonBackReference
    private PropertyRealty propertyRealty;
    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Address address;
    @ManyToOne
    @JoinColumn(name = "SELLER_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Seller seller;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;
    @ManyToMany(mappedBy = "adverts")
    private List<Review> reviews;

    public Long getId() {
        return id;
    }

    public TypeRealty getTypeRealty() {
        return typeRealty;
    }

    public void setTypeRealty(TypeRealty typeRealty) {
        this.typeRealty = typeRealty;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinalUrl() {
        return finalUrl;
    }

    public void setFinalUrl(String finalUrl) {
        this.finalUrl = finalUrl;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public PropertyRealty getPropertyRealty() {
        return propertyRealty;
    }

    public void setPropertyRealty(PropertyRealty propertyRealty) {
        this.propertyRealty = propertyRealty;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advert advert = (Advert) o;
        return Objects.equals(id, advert.id) && typeRealty == advert.typeRealty && Objects.equals(publishedAt, advert.publishedAt) && Objects.equals(editedAt, advert.editedAt) && Objects.equals(description, advert.description) && Objects.equals(finalUrl, advert.finalUrl) && status == advert.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeRealty, publishedAt, editedAt, description, finalUrl, status);
    }
}
