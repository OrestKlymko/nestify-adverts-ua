package org.ui.main.services.advert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.ui.main.services.seller.enums.TypeOwner;

@Schema(description = "Create Seller")
public record SellerCreateRequest(
		@Schema(description = "Seller authenticate ID from Keycloak service")
		String sellerAuthId,
        String nameOwner,
        TypeOwner typeOwner,
        String numberPhone,
		@Schema(description = "If not OWNER")
		String agencyName
) {
}
