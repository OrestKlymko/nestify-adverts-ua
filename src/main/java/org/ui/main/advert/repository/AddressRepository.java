package org.ui.main.advert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.model.Address;

import java.util.Optional;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select a from Address a where upper(a.addressName) = upper(?1) and upper(a.district) = upper(?2) and upper(a.city) = upper(?3)")
    Optional<Address> findAddressByAddressNameAndDistrictAndCity(String addressName, String district, String city);
}
