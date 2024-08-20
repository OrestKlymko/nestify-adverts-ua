package org.ui.main.services.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.services.address.model.DistrictPart;

import java.util.Optional;

@Repository
public interface DistrictPartRepository extends JpaRepository<DistrictPart,Long> {
    Optional<DistrictPart> getDistrictPartByDistrictName(String name);
}
