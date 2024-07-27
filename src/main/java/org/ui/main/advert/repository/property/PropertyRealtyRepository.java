package org.ui.main.advert.repository.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.model.property.PropertyRealty;

@Repository
public interface PropertyRealtyRepository extends JpaRepository<PropertyRealty,Long> {
}
