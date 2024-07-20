package org.ui.main.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ui.main.search.dto.FilterSearchResponse;
import org.ui.main.search.dto.PageResponse;
import org.ui.main.search.service.SearchService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

//	@GetMapping
//	public PageResponse<FilterSearchResponse> filterSearchForm(@RequestParam Map<String, List<String>> searchRequestDto) {
//		return searchService.filterSearchForm(searchRequestDto);
//	}


}
