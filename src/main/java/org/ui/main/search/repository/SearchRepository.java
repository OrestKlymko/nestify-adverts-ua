package org.ui.main.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.model.Advert;
import org.ui.main.search.dto.PriceRangeStatisticResponse;

import java.util.List;


@Repository
public interface SearchRepository extends JpaRepository<Advert, Long> {
    @Query(value = """
            SELECT
                FLOOR(property_building.total_price / :interval) * :interval AS priceRange,
                COUNT(*) AS count
            FROM
                property_building
            GROUP BY
                priceRange
            ORDER BY
                priceRange;
            """, nativeQuery = true)
    List<PriceRangeStatisticResponse> getStatistic(@Param("interval") int interval);
}
