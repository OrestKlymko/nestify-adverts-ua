package org.ui.main.advert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.model.Advantages;
import org.ui.main.advert.model.enums.Advantage;

import java.util.List;
import java.util.Set;

@Repository
public interface AdvantageRepository extends JpaRepository<Advantages, Long> {
    List<Advantages> findAdvantagesByAdvantageNameIn(Set<Advantage> advantageName);
}
