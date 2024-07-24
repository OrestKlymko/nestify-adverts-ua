package org.ui.main.ai.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ui.main.ai.dto.AiResponse;
import org.ui.main.exceptions.NotFondException;
import org.ui.main.search.dto.FilterSearchResponse;
import org.ui.main.search.dto.PageResponse;
import org.ui.main.search.service.SearchService;
import org.ui.main.ai.dto.AiRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class AiService {
    private final SearchService searchService;
    private final RestTemplate restTemplate;

    public AiService(SearchService searchService, RestTemplate restTemplate) {
        this.searchService = searchService;
        this.restTemplate = restTemplate;
    }

    public PageResponse<FilterSearchResponse> getAdvertByAI(AiRequest query) {
        AiResponse response = null;
        try {
            response = callAi(query);
        } catch (NotFondException e) {
            throw new RuntimeException(e);
        }
        if (response != null) {
            HashMap<String, List<String>> map = new HashMap<>();
            String[] split = response.query().replace("?", "").split("&");
            for (String s : split) {
                String[] pair = s.split("=");
                List<String> value = Arrays.stream(pair[1].split(",")).toList();
                map.put(pair[0], value);
            }
            return searchService.filterSearchForm(map);
        }
        return null;
    }

    public AiResponse callAi(AiRequest request) throws NotFondException {
        ResponseEntity<AiResponse> aiResponseResponseEntity =
                restTemplate.postForEntity("https://ai-python-nestify-v1-production.up.railway.app", request, AiResponse.class);
        if (aiResponseResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) &&
                aiResponseResponseEntity.hasBody()) {
            return aiResponseResponseEntity.getBody();
        }
        throw new NotFondException("Not found");
    }
}
