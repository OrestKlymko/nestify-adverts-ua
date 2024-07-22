package org.ui.main.advert.dto;

import org.ui.main.advert.model.enums.TypeRealty;

import java.util.Set;

public record CreateAdvertRequest(
        TypeRealty typeRealty,
        String description,
        String finalUrl,
        Set<String> images,
        PropertyCreateRequest property,
        AddressCreateRequest address,
        SellerCreateRequest seller
) {
}
