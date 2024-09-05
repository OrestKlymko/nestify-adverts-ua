package org.ui.main.services.search.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.ui.main.services.search.dto.FilterSearchResponse;
import org.ui.main.services.search.dto.PageResponse;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ContextConfiguration
class SearchControllerTest {

	@Autowired
	private SearchController searchController;

	@Test
	void filterSearchForm() {
		HashMap<String, List<String>> parameters = new HashMap<>();

		parameters.put("city", List.of("Bratislava"));
		parameters.put("rooms", List.of("1,2,3"));

		PageResponse<FilterSearchResponse> filterSearchResponsePageResponse = searchController.filterSearchForm(parameters);
		int roomValue = filterSearchResponsePageResponse.getPage().getContent().get(0).room();

		assertNotNull(filterSearchResponsePageResponse);
		assertNotNull(filterSearchResponsePageResponse.getPage());
		assertNotNull(filterSearchResponsePageResponse.getAdvertsOnMap());
		assertNotNull(filterSearchResponsePageResponse.getMaxPrice());

		assertTrue(roomValue == 1 || roomValue == 2 || roomValue == 3);
		assertEquals(2, filterSearchResponsePageResponse.getPage().getContent().size());
	}
}