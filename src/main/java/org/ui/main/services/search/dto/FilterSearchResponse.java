package org.ui.main.services.search.dto;

import org.ui.main.services.advert.model.enums.ApartmentFeature;
import org.ui.main.services.advert.model.enums.Status;

import java.time.LocalDateTime;
import java.util.Set;

public record FilterSearchResponse(
		Long id,
		double longitude,
		double latitude,
		long price,
		int room,
		float square,
		int floor,
		String address,
		Set<String> features,
		String description,
		LocalDateTime publishedAt,
		String district,
		long buildIdMapTiler,
		String advertImage,
		Status status,
		String agencyCatalog
) {
}
