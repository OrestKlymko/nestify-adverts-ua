package org.ui.main.services.feature.service;

import org.springframework.stereotype.Service;
import org.ui.main.services.feature.dto.FeatureResponse;
import org.ui.main.services.feature.repository.FeatureRepository;

import java.util.List;

@Service
public class FeatureService {
	private final FeatureRepository featureRepository;

	public FeatureService(FeatureRepository featureRepository) {
		this.featureRepository = featureRepository;
	}

	public List<FeatureResponse> getAllFeatures(String lang) {
		return featureRepository.findFeaturesByLang(lang);
	}
}
