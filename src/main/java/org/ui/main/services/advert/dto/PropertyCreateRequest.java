package org.ui.main.services.advert.dto;

import org.ui.main.services.advert.model.enums.Advantage;
import org.ui.main.services.advert.model.enums.AllowedStatus;
import org.ui.main.services.advert.model.enums.ApartmentFeature;

import java.util.Set;

public record PropertyCreateRequest(
        float square,
        int floor,
        int room,
        int realtyPrice,
        int energyPrice,
        int maxPerson,
        int totalPrice,
        Set<ApartmentFeature> features,
        AllowedStatus withPets,
        AllowedStatus withKids,
        Set<Advantage> advantages
        ) {
}
