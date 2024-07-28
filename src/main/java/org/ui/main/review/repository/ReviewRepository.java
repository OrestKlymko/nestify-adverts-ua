package org.ui.main.review.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ui.main.review.dto.ReviewOnAdvertResponse;
import org.ui.main.review.model.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO REVIEWS_ADVERTS (ADVERT_ID, REVIEW_ID) VALUES (?1, ?2)", nativeQuery = true)
    void insertReviewOnAdvert(Long advertId, Long reviewId);

    @Query(value = """

            SELECT reviews.name      as reviewName,
                    count(reviews.id) as countReview
                    reviews_adverts.advert_id as advertId
            FROM reviews_adverts
            JOIN reviews ON reviews_adverts.review_id = reviews.id
                                            WHERE advert_id = :advertId
                                            GROUP BY reviews.name, advert_id
                                            HAVING count(reviews.id) >= 3
            """, nativeQuery = true)
    public List<ReviewOnAdvertResponse> getReviewsOnAdvert(@Param("advertId") Long advertId);
}
