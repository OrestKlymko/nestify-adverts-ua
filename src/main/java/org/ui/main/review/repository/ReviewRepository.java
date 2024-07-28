package org.ui.main.review.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ui.main.review.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO REVIEWS_ADVERTS (ADVERT_ID, REVIEW_ID) VALUES (?1, ?2)", nativeQuery = true)
    void insertReviewOnAdvert(Long advertId, Long reviewId);
}
