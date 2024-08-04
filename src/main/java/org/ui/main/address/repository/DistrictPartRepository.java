package org.ui.main.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.address.model.DistrictPart;

import java.util.Optional;

@Repository
public interface DistrictPartRepository extends JpaRepository<DistrictPart,Long> {
    Optional<DistrictPart> getDistrictPartByDistrictName(String name);
}
