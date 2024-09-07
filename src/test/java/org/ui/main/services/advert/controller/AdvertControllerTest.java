package org.ui.main.services.advert.controller;

import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.ui.main.exceptions.NotFondException;
import org.ui.main.services.address.dto.AddressCreateRequest;
import org.ui.main.services.address.dto.CreateAdvertRequest;
import org.ui.main.services.advert.dto.FinalPageResponse;
import org.ui.main.services.advert.dto.PropertyCreateRequest;
import org.ui.main.services.advert.model.enums.Advantage;
import org.ui.main.services.advert.model.enums.AllowedStatus;
import org.ui.main.services.advert.model.enums.ApartmentFeature;
import org.ui.main.services.advert.model.enums.TypeRealty;
import org.ui.main.services.seller.enums.TypeOwner;

import java.io.File;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdvertControllerTest {

	@Autowired
	private AdvertController advertController;

	@Test
	void getAdvert() throws NotFondException {
		ResponseEntity<FinalPageResponse> advert = advertController.getAdvert(1L);

		assertNotNull(advert);
		assertNotNull(advert.getBody());
		assertEquals(200, advert.getStatusCode().value());
		assertEquals(1, advert.getBody().getId());
	}

	@Test
	void createAdvert() {

		PropertyCreateRequest propertyCreateRequest = new PropertyCreateRequest(
				2.0f,
				2,
				1,
				1000,
				2,
				50,
				1050,
				Set.of(ApartmentFeature.GARAGE, ApartmentFeature.WASHING_MACHINE),
				AllowedStatus.YES,
				AllowedStatus.NO,
				Set.of(Advantage.CENTER, Advantage.GOOD_TRANSPORT_STATE)
		);

		AddressCreateRequest addressCreateRequest = new AddressCreateRequest(
				1,
				"Petrzalka",
				"Znievska",
				"Bratislava",
				1);

		CreateAdvertRequest advertRequest = new CreateAdvertRequest(
				null,
				TypeRealty.FLAT,
				"Some description",
				"https://example.com",
				"3bed581f-da62-4333-ae25-1285049289ac",
				Set.of("https://example.com/image1.jpg", "https://example.com/image2.jpg"),
				propertyCreateRequest,
				addressCreateRequest,
				TypeOwner.NESTIFY_RENT_OWNER
		);


		ResponseEntity<Void> advert = advertController.createAdvert(advertRequest);

		assertNotNull(advert);
		assertEquals(HttpStatus.CREATED, advert.getStatusCode());
	}

}