package org.ui.main.services.search.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.ui.main.services.search.dto.FilterSearchResponse;
import org.ui.main.services.search.dto.PageResponse;
import org.ui.main.services.search.service.SearchService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@Tag(name = "Search")
public class SearchController {


	private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
	@Operation(summary = "Get advert on map by search filter")
	public PageResponse<FilterSearchResponse> filterSearchForm(
			@Parameter(
					description = "Search parameters: \n" +
							"- **city**: City where the advertisement is located (e.g., Kyiv)\n" +
							"- **address**: Address to search for (e.g., Shevchenko street)\n" +
							"- **rooms**: Number of rooms (e.g., 1,2,3)\n" +
							"- **features**: Property features (e.g., balcony,garage)\n" +
							"- **district**: District where the advertisement is located (e.g., Pecherskyi)\n" +
							"- **typeOwner**: Type of owner (private, agency)\n" +
							"- **withKids**: Whether children are allowed (e.g., true)\n" +
							"- **withPets**: Whether pets are allowed (e.g., true)\n" +
							"- **typeRealty**: Type of real estate (e.g., apartment)\n" +
							"- **agency**: Agency name (e.g., MyAgency)\n" +
							"- **priceFrom**: Minimum price (e.g., 1000)\n" +
							"- **priceTo**: Maximum price (e.g., 5000)\n" +
							"- **sort**: Sorting strategy (newest, lowPrice, highPrice)\n" +
							"- **offset**: Starting index for pagination (e.g., 0)\n" +
							"- **limit**: Number of records per page (e.g., 12)" +
							"url should be encoded",
					example = "?city=Bratislava&address=Shevchenko%2street&rooms=1,2,3"
			)
			@RequestParam Map<String, List<String>> searchRequestDto

	) {
		return searchService.filterSearchForm(searchRequestDto);
	}
}


