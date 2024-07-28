package org.ui.main.review.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ui.main.review.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{advertId}/{reviewId}")
    public void createReview(@PathVariable Long advertId, @PathVariable Long reviewId){
        reviewService.createReviewOnAdvert(advertId,reviewId);
    }
}
