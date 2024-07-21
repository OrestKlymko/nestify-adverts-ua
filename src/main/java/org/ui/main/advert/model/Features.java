package org.ui.main.advert.model;


import jakarta.persistence.*;
import org.ui.main.advert.model.enums.ApartmentFeature;

@Entity
@Table(name = "FEATURES")
public class Features {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FEATURE")
    @Enumerated(EnumType.STRING)
    private ApartmentFeature feature;
    @ManyToOne
    @JoinColumn(name = "PROPERTY_ID", referencedColumnName = "ID")
    private PropertyRealty propertyRealty;

}
