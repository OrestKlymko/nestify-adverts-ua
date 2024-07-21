package org.ui.main.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class PageResponse<Advert> {
	@JsonProperty("strategySort")
	private String strategySort;

	@JsonProperty("maxPrice")
	private Long maxPrice;

	@JsonProperty("statistics")
	private Map<Integer, Integer> statistics;

	@JsonProperty("advertsOnMap")
	private List<CoordinateResponse> advertsOnMap;

	@JsonProperty("page")
	private Page<Advert> page;

	public PageResponse(Page<Advert> page, String strategySort, Long maxPrice, Map<Integer, Integer> statistics, List<CoordinateResponse> advertsOnMap) {
		this.page = page;
		this.strategySort = strategySort;
		this.maxPrice = maxPrice;
		this.statistics = statistics;
		this.advertsOnMap = advertsOnMap;
	}

	public String getStrategySort() {
		return strategySort;
	}

	public void setStrategySort(String strategySort) {
		this.strategySort = strategySort;
	}

	public Long getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Long maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Map<Integer, Integer> getStatistics() {
		return statistics;
	}

	public void setStatistics(Map<Integer, Integer> statistics) {
		this.statistics = statistics;
	}

	public List<CoordinateResponse> getAdvertsOnMap() {
		return advertsOnMap;
	}

	public void setAdvertsOnMap(List<CoordinateResponse> advertsOnMap) {
		this.advertsOnMap = advertsOnMap;
	}

	public Page<Advert> getPage() {
		return page;
	}

	public void setPage(Page<Advert> page) {
		this.page = page;
	}
}
