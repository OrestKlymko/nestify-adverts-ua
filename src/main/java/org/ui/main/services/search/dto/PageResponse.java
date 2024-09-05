package org.ui.main.services.search.dto;

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

	public List<CoordinateResponse> getAdvertsOnMap() {
		return advertsOnMap;
	}

	public void setAdvertsOnMap(List<CoordinateResponse> advertsOnMap) {
		this.advertsOnMap = advertsOnMap;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Page<FilterSearchResponse> getPage() {
		return page;
	}

	public void setPage(Page<FilterSearchResponse> page) {
		this.page = page;
	}

	public Map<Integer, Integer> getStatistics() {
		return statistics;
	}

	public void setStatistics(Map<Integer, Integer> statistics) {
		this.statistics = statistics;
	}

	public String getStrategySort() {
		return strategySort;
	}

	public void setStrategySort(String strategySort) {
		this.strategySort = strategySort;
	}

	@Override
	public String toString() {
		return "PageResponse{" +
				"advertsOnMap=" + advertsOnMap +
				", strategySort='" + strategySort + '\'' +
				", maxPrice=" + maxPrice +
				", statistics=" + statistics +
				", page=" + page +
				'}';
	}
}
