package org.ui.main.services.advert.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ui.main.services.address.dto.CreateAdvertRequest;
import org.ui.main.services.advert.dto.FinalPageResponse;
import org.ui.main.services.advert.service.AdvertService;
import org.ui.main.exceptions.NotFondException;

@RestController
@RequestMapping("/api/advert")
@Tag(name = "Adverts")
public class AdvertController {
    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get info of advert for final page")
    public ResponseEntity<FinalPageResponse> getAdvert(
            @Parameter(description = "Advert ID", required = true)
            @PathVariable Long id
    ) throws NotFondException {
            return ResponseEntity.ok(advertService.findAdvertById(id));
    }

    @PostMapping
    @Operation(summary = "Create new advert")
    public ResponseEntity<Void> createAdvert(@RequestBody CreateAdvertRequest advertRequest) {
        advertService.createAdvert(advertRequest);
	    return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @ExceptionHandler(NotFondException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundException(NotFondException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
