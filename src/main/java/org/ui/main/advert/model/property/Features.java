package org.ui.main.advert.model.property;


import jakarta.persistence.*;
import org.ui.main.advert.model.enums.ApartmentFeature;

import java.util.List;

@Entity
@Table(name = "FEATURES")
public class Features {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FEATURE")
    @Enumerated(EnumType.STRING)
    private ApartmentFeature featureName;
    @ManyToMany(mappedBy = "features")
    private List<PropertyRealty> propertyRealty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApartmentFeature getFeatureName() {
        return featureName;
    }

}
