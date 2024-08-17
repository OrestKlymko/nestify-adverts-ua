package org.ui.main.advert.dto;

import org.ui.main.seller.enums.TypeOwner;

public record SellerCreateRequest(
        String sellerAuthId,
        String nameOwner,
        TypeOwner typeOwner,
        String numberPhone,
        String agencyName
) {
}
