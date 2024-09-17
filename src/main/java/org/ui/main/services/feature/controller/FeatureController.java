package org.ui.main.services.feature.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ui.main.services.feature.dto.FeatureResponse;
import org.ui.main.services.feature.service.FeatureService;

import java.util.List;

@RestController
@RequestMapping("/api/features")
public class FeatureController {
	private final FeatureService featureService;

	public FeatureController(FeatureService featureService) {
		this.featureService = featureService;
	}

	@GetMapping("/{lang}")
	public List<FeatureResponse> getAllFeatures(@PathVariable String lang) {
		return featureService.getAllFeatures(lang);
	}
}
