package org.ui.main.advert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.model.PropertyRealty;

@Repository
public interface PropertyRealtyRepository extends JpaRepository<PropertyRealty,Long> {
}
