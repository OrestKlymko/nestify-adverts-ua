package org.ui.main.services.advert.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.ui.main.exceptions.NotFondException;
import org.ui.main.services.advert.dto.FinalPageResponse;
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

}