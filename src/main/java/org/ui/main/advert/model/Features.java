package org.ui.main.advert.model;


import jakarta.persistence.*;
import org.ui.main.advert.model.enums.ApartmentFeature;

import java.util.List;

@Entity
@Table(name = "FEATURES")
public class Features {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FEATURE")
    @Enumerated(EnumType.STRING)
    private ApartmentFeature featureName;
    @ManyToMany(mappedBy = "features")
    private List<PropertyRealty> propertyRealty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ApartmentFeature getFeatureName() {
        return featureName;
    }

}
