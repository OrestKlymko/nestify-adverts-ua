package org.ui.main.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.ui.main.ai.dto.HousePointCoordinates;

import java.util.List;
import java.util.Map;

public class PageResponse<FilterSearchResponse> extends PageImpl<FilterSearchResponse> {
	@JsonProperty("strategySort")
	private String strategySort;

	@JsonProperty("maxPrice")
	private Long maxPrice;

	@JsonProperty("statistics")
	private Map<Integer, Integer> statistics;

	@JsonProperty("advertsOnMap")
	private List<HousePointCoordinates> advertsOnMap;

	public PageResponse(List<FilterSearchResponse> content, Pageable pageable, long total, String strategySort, Long maxPrice, Map<Integer, Integer> statistics, List<HousePointCoordinates> advertsOnMap) {
		super(content, pageable, total);
		this.strategySort = strategySort;
		this.maxPrice = maxPrice;
		this.statistics = statistics;
		this.advertsOnMap = advertsOnMap;
	}
}
