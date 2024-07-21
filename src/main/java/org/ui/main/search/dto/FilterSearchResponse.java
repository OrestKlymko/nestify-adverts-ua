package org.ui.main.search.dto;

import org.ui.main.advert.model.enums.Advantage;
import org.ui.main.advert.model.enums.ApartmentFeature;

import java.time.LocalDateTime;
import java.util.List;

public record FilterSearchResponse(
		String id,
		double longitude,
		double latitude,
		long price,
		int room,
		float square,
		int floor,
		String address,
		List<ApartmentFeature> features,
		String description,
		List<Advantage> advantagesList,
		LocalDateTime published_at,
		String district,
		long buildIdMapTiler,
		String advertImage,
		Boolean isApprove,
		String agencyCatalog
) {
}
