package org.ui.main.services.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ui.main.services.feature.dto.FeatureResponse;
import org.ui.main.services.feature.model.Features;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Features, Long> {

	@Query(value = """
			SELECT
			    features.feature_key AS featureKey,
			    CASE
			        WHEN :lang = 'ua' THEN features.feature_value_ua
			        ELSE features.feature_value_ru
			    END AS featureValue
			FROM
			    features
			""", nativeQuery = true)
	List<FeatureResponse> findFeaturesByLang(@Param("lang") String lang);
}
