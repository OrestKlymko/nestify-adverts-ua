package org.ui.main.advert.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.model.address.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
