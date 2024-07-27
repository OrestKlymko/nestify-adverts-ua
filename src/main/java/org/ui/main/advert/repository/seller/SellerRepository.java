package org.ui.main.advert.repository.seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.model.seller.Seller;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Optional<Seller> findByNameOwnerIgnoreCase(String nameOwner);
}
