package org.ui.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.model.Advert;

import java.util.UUID;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, UUID> {
}
