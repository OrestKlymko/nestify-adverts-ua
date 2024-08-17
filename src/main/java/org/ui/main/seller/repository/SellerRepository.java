package org.ui.main.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.seller.model.Seller;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Optional<Seller> findByNameOwnerIgnoreCase(String nameOwner);
    Optional<Seller> findBySellerAuthId(String sellerAuthId);
}
