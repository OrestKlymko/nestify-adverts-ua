package org.ui.main.advert.repository.seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.model.seller.Agency;

import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency,Long> {
    @Query("select a from Agency a where upper(a.agencyName) = upper(?1)")
    Optional<Agency> getAgencyByAgencyNameIsIgnoreCase(String agencyName);
}
