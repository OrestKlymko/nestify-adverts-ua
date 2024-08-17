package org.ui.main.address.dto;

import org.ui.main.advert.dto.PropertyCreateRequest;
import org.ui.main.advert.dto.SellerCreateRequest;
import org.ui.main.advert.model.enums.TypeRealty;

import java.io.File;
import java.util.Set;

public record CreateAdvertRequest(
        Long idApplication,
        TypeRealty typeRealty,
        String description,
        String finalUrl,
        String sellerAuthId,
        Set<File> images,
        PropertyCreateRequest property,
        AddressCreateRequest address

) {
}
