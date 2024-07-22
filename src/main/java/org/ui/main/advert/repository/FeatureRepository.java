package org.ui.main.advert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.model.Features;
import org.ui.main.advert.model.enums.ApartmentFeature;

import java.util.List;
import java.util.Set;

@Repository
public interface FeatureRepository extends JpaRepository<Features,Long> {
    List<Features> findFeaturesByFeatureNameIn(Set<ApartmentFeature> featureName);
}
