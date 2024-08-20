package org.ui.main.services.advert.repository.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.services.advert.model.property.PropertyRealty;

@Repository
public interface PropertyRealtyRepository extends JpaRepository<PropertyRealty,Long> {
}
