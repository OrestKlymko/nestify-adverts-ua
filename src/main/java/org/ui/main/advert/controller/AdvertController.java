package org.ui.main.advert.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ui.main.address.dto.CreateAdvertRequest;
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
    public ResponseEntity<FinalPageResponse> getAdvert(@PathVariable Long id) throws NotFondException {
            return ResponseEntity.ok(advertService.findAdvertById(id));
    }

    @PostMapping
    public void createAdvert(@RequestBody CreateAdvertRequest advertRequest) {
        advertService.createAdvert(advertRequest);
    }


    @ExceptionHandler(NotFondException.class)
    public ResponseEntity<?> handleNotFoundException(NotFondException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
