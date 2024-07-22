package org.ui.main.advert.dto;

import org.ui.main.advert.model.enums.TypeOwner;

public record SellerCreateRequest(
        String nameOwner,
        TypeOwner typeOwner,
        String numberPhone,
        String agencyName
) {
}
