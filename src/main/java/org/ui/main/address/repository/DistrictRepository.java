package org.ui.main.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.address.model.District;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District,Long> {
    Optional<District> getDistrictByDistrictName(String name);
}
