package org.ui.main.ai.controller;


import org.springframework.web.bind.annotation.*;
import org.ui.main.advert.model.Advert;
import org.ui.main.ai.dto.AiRequest;
import org.ui.main.ai.service.AiService;
import org.ui.main.search.dto.FilterSearchResponse;
import org.ui.main.search.dto.PageResponse;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping
    public PageResponse<FilterSearchResponse> getAiResult(@RequestBody AiRequest request){
        return aiService.getAdvertByAI(request);
    }
}
