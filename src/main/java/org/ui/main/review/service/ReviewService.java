package org.ui.main.review.service;


import org.springframework.stereotype.Service;
import org.ui.main.review.repository.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    public void createReviewOnAdvert(Long advertId, Long reviewId) {
        reviewRepository.insertReviewOnAdvert(advertId,reviewId);
    }
}
