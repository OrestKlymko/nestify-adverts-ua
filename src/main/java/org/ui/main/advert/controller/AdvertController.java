package org.ui.main.advert.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ui.main.advert.service.AdvertService;
import org.ui.main.advert.dto.FinalPageResponse;
import org.ui.main.exceptions.NotFondException;

@RestController
@RequestMapping("/api/advert")
public class AdvertController {
    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @GetMapping("/{id}")
    public FinalPageResponse getAdvert(@PathVariable Long id) {
        try {
            return advertService.findAdvertById(id);
        } catch (NotFondException e) {
            throw new RuntimeException(e);
        }
    }

}
