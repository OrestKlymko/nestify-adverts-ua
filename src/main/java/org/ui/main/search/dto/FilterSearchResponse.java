package org.ui.main.search.dto;

import org.ui.main.advert.model.enums.Advantage;
import org.ui.main.advert.model.enums.ApartmentFeature;
import org.ui.main.advert.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
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
		Set<ApartmentFeature> features,
		String description,
		Set<Advantage> advantagesList,
		LocalDateTime published_at,
		String district,
		long buildIdMapTiler,
		String advertImage,
		Status status,
		String agencyCatalog
) {
}
