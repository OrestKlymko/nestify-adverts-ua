package org.ui.main.input.ai.controller;


import org.springframework.web.bind.annotation.*;
import org.ui.main.input.ai.dto.AiRequest;
import org.ui.main.input.ai.service.AiService;
import org.ui.main.services.search.dto.FilterSearchResponse;
import org.ui.main.services.search.dto.PageResponse;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping
    public PageResponse<FilterSearchResponse> getAiResult(@RequestBody AiRequest request) {
        return aiService.getAdvertByAI(request);
    }
}
