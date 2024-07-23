package org.ui.main.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public class PageResponse<Advert> {
	@JsonProperty("strategySort")
	private String strategySort;

	@JsonProperty("maxPrice")
	private Integer maxPrice;

	@JsonProperty("statistics")
	private Map<Integer, Integer> statistics;

	@JsonProperty("advertsOnMap")
	private List<CoordinateResponse> advertsOnMap;

	@JsonProperty("page")
	private Page<FilterSearchResponse> page;

	public PageResponse(Page<FilterSearchResponse> page, String strategySort, Integer maxPrice, Map<Integer, Integer> statistics, List<CoordinateResponse> advertsOnMap) {
		this.page = page;
		this.strategySort = strategySort;
		this.maxPrice = maxPrice;
		this.statistics = statistics;
		this.advertsOnMap = advertsOnMap;
	}
}
