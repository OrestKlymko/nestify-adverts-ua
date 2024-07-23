package org.ui.main.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.model.Advert;
import org.ui.main.map.dto.MapAdvertsResponse;
import org.ui.main.map.dto.PointMapResponse;

import java.util.List;


@Repository
public interface MapRepository extends JpaRepository<Advert, Long> {

	@Query(value = """
			SELECT address.id as addressHouseId,
			       address.build_map_tiler as buildIdMapTiler,
			       address.longitude as longitude,
			       address.latitude as latitude,
			       (SELECT MIN(property_building.total_price)
			        FROM property_building
			        JOIN adverts ON property_building.id = adverts.property_id
			        WHERE adverts.address_id = address.id) as lowerPrice
			FROM address
			WHERE address.id IN (:ids)
			GROUP BY address.id,
			         address.build_map_tiler,
			         address.longitude,
			         address.latitude""", nativeQuery = true)
	List<PointMapResponse> getPointsMap(@Param("ids") List<Long> addressId);

	@Query(value = """
			 SELECT adverts.id as advertId,
			     adverts.description as description,
			     address.district as district,
			     address.address_name as address,
			     address.build_map_tiler as buildIdMapTiler,
			     address.longitude as longitude,
			     address.latitude as latitude,
			     property_building.square as square,
			     property_building.floor as floor,
			     property_building.room as room,
			     property_building.total_price as price,
			     agency.agency_catalog as agencyCatalog,
			     adverts.published_at as publishedAt,
			     (SELECT image_url FROM unnest(ARRAY_AGG(images.image_url)) LIMIT 1) as advertImage,
			     ARRAY_AGG(DISTINCT features.feature) as features,
			     ARRAY_AGG(DISTINCT advantages.advantage) as advantages
			 FROM adverts
			 JOIN address ON adverts.address_id = address.id
			 JOIN property_building ON adverts.property_id = property_building.id
			 JOIN seller on adverts.seller_id = seller.id
			 LEFT JOIN images on adverts.id = images.advert_id
			 LEFT JOIN agency on seller.agency_id = agency.id
			 LEFT JOIN property_features pf on property_building.id = pf.property_id
			 LEFT JOIN features on pf.feature_id = features.id
			 LEFT JOIN property_advantages pa on property_building.id = pa.property_id
			 LEFT JOIN advantages on pa.advantage_id = advantages.id
			 WHERE address.id = :addressId
			 GROUP BY 
			     adverts.id,
			     adverts.description,
			     address.district,
			     address.address_name,
			     address.build_map_tiler,
			     address.longitude,
			     address.latitude,
			     adverts.published_at,
			     agency.agency_catalog,
			     property_building.square,
			     property_building.floor,
			     property_building.room,
			     property_building.total_price
			""", nativeQuery = true)
	List<MapAdvertsResponse> getAdvertsOnMap(@Param("addressId") Long addressId);
}

