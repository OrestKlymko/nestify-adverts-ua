package org.ui.main.advert.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.dto.AllLocationDto;
import org.ui.main.advert.model.address.Address;

import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(value = """
            SELECT city.city_name as city, districts.district_name as district, street.street_name as street
            FROM street
                     JOIN districts ON street.district_id = districts.id
                     JOIN city ON districts.city_id = city.id
            """, nativeQuery = true)
    List<AllLocationDto> getAllLocation();
}
