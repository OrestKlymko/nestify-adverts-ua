package org.ui.main.services.feature.model;


import jakarta.persistence.*;
import org.ui.main.services.advert.model.property.PropertyRealty;

import java.util.List;

@Entity
@Table(name = "FEATURES")
public class Features {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FEATURE_KEY")
    private String featureKey;

    @Column(name = "FEATURE_VALUE_RU")
    private String featureValueRu;

    @Column(name = "FEATURE_VALUE_UA")
    private String featureValueUa;

    @ManyToMany(mappedBy = "features")
    private List<PropertyRealty> propertyRealty;

    public String getFeatureKey() {
        return featureKey;
    }

    public void setFeatureKey(String featureKey) {
        this.featureKey = featureKey;
    }

    public String getFeatureValueRu() {
        return featureValueRu;
    }

    public void setFeatureValueRu(String featureValueRu) {
        this.featureValueRu = featureValueRu;
    }

    public String getFeatureValueUa() {
        return featureValueUa;
    }

    public void setFeatureValueUa(String featureValueUa) {
        this.featureValueUa = featureValueUa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
