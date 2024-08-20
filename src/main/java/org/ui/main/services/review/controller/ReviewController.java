package org.ui.main.services.review.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ui.main.services.review.service.ReviewService;

@RestController
@RequestMapping("/api/review")
@Tag(name = "Search")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{advertId}/{reviewId}")
    @Operation(summary = "Create review on post. Should post advertId and reviewId")
    public void createReview(@PathVariable Long advertId, @PathVariable Long reviewId){
        reviewService.createReviewOnAdvert(advertId,reviewId);
    }
}
