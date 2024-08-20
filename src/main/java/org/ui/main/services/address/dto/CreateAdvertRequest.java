package org.ui.main.services.address.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.ui.main.services.advert.dto.PropertyCreateRequest;
import org.ui.main.services.advert.model.enums.TypeRealty;

import java.io.File;
import java.util.Set;
@Schema(description = "Create advert request")
public record CreateAdvertRequest(
		@Schema(description = "Id application from admin panel",nullable = true)
		Long idApplication,
		@Schema(description = "Type of realty")
		TypeRealty typeRealty,
		@Schema(description = "Description")
		String description,
		@Schema(description = "Final Url if that is advert from another website",nullable = true)
        String finalUrl,
		@Schema(description = "Seller authenticate ID from Keycloak service")
		String sellerAuthId,
		@Schema(description = "Images")
		Set<File> images,
		@Schema(description = "Main property")
		PropertyCreateRequest property,
		@Schema(description = "Address")
		AddressCreateRequest address
) {
}
