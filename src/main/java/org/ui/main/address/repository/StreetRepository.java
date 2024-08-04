package org.ui.main.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ui.main.address.model.Street;

import java.util.Optional;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {
    @Query(value = """
            SELECT street.id, street.street_name, street.street_number,street.district_id FROM street
            JOIN districts ON street.district_id = districts.id
            JOIN city ON districts.city_id = city.id
            WHERE street.street_name = :streetName
                         AND street.street_number = :streetNumber
                         AND districts.district_name = :districtName
                         AND city.city_name = :cityName
                       LIMIT 1""", nativeQuery = true)
    Optional<Street> findStreet(String streetName, Integer streetNumber, String districtName, String cityName);
}
