package org.ui.main.ai.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ui.main.ai.dto.AiRequest;
import org.ui.main.ai.dto.AiResponse;
import org.ui.main.exceptions.NotFondException;
import org.ui.main.search.dto.FilterSearchResponse;
import org.ui.main.search.dto.PageResponse;
import org.ui.main.search.service.SearchService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiService {

    private final SearchService searchService;
    private final RestTemplate restTemplate;
    private static final int MAX_RETRIES = 10;

    public AiService(SearchService searchService, RestTemplate restTemplate) {
        this.searchService = searchService;
        this.restTemplate = restTemplate;
    }

    public PageResponse<FilterSearchResponse> getAdvertByAI(AiRequest query) {
        AiResponse response = null;
        int attempt = 0;
        boolean success = false;

        while (attempt < MAX_RETRIES && !success) {
            try {
                response = callAi(query);
                success = true;
            } catch (NotFondException e) {
                attempt++;
                if (attempt >= MAX_RETRIES) {
                    throw new RuntimeException("AI service did not return a valid query after " + MAX_RETRIES + " attempts", e);
                }
            } catch (Exception e) {
                throw new RuntimeException("An error occurred while processing the AI query", e);
            }
        }

        HashMap<String, List<String>> map = new HashMap<>();
        String[] split = response.query().replace("?", "").split("&");
        for (String s : split) {
            String[] pair = s.split("=");
            List<String> value = Arrays.stream(pair[1].split(",")).collect(Collectors.toList());
            map.put(pair[0], value);
        }
        return searchService.filterSearchForm(map);
    }

    public AiResponse callAi(AiRequest request) throws NotFondException {
        ResponseEntity<AiResponse> aiResponseResponseEntity =
                restTemplate.postForEntity("https://ai-python-nestify-v1-production.up.railway.app", request, AiResponse.class);

        if (aiResponseResponseEntity.getStatusCode() == HttpStatus.OK && aiResponseResponseEntity.hasBody()) {
            return aiResponseResponseEntity.getBody();
        } else {
            throw new NotFondException("Not found");
        }
    }

    // Ви можете додати свої класи винятків та інші допоміжні класи тут
}