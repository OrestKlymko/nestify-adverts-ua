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
                                                 FROM adverts
                                                 LEFT JOIN address ON adverts.address_id = address.id
                                                 WHERE adverts.id IN (:ids)
                                                 GROUP BY address.id,
                                                          address.build_map_tiler,
                                                          address.longitude,
                                                          address.latitude""", nativeQuery = true)
    List<PointMapResponse> getPointsMap(@Param("ids") List<Long> addressId);

    @Query(value = """
            SELECT adverts.id                                             AS id,
                  adverts.description                                    AS description,
                  districts.district_name                                AS district,
                  concat(street.street_name, ', ', street.street_number) AS address,
                  address.build_map_tiler                                AS buildIdMapTiler,
                  address.longitude                                      AS longitude,
                  address.latitude                                       AS latitude,
                  property_building.square                               AS square,
                  property_building.floor                                AS floor,
                  property_building.room                                 AS room,
                  property_building.total_price                          AS price,
                  agency.agency_catalog                                  AS agencyCatalog,
                  adverts.published_at                                   AS publishedAt,
                  img.image_url                                          AS advertImage,
                  ARRAY_AGG(DISTINCT features.feature)                   AS features,
                  ARRAY_AGG(DISTINCT advantages.advantage)               AS advantages
            FROM adverts
                    LEFT JOIN
                address ON adverts.address_id = address.id
                    LEFT JOIN
                city ON address.city_id = city.id
                    LEFT JOIN
                districts ON address.district_id = districts.id
                    LEFT JOIN
                street ON address.street_id = street.id
                    LEFT JOIN
                property_building ON adverts.property_id = property_building.id
                    LEFT JOIN
                seller ON adverts.seller_id = seller.id
                    LEFT JOIN
                agency ON seller.agency_id = agency.id
                    LEFT JOIN
                property_features pf ON property_building.id = pf.property_id
                    LEFT JOIN
                features ON pf.feature_id = features.id
                    LEFT JOIN
                property_advantages pa ON property_building.id = pa.property_id
                    LEFT JOIN
                advantages ON pa.advantage_id = advantages.id
                    LEFT JOIN LATERAL
               (SELECT image_url
                FROM images
                WHERE images.advert_id = adverts.id
                ORDER BY images.image_url
                LIMIT 1) img ON true
            WHERE address.id = :addressId
            GROUP BY adverts.id,
                    adverts.description,
                    street_name,
                    street_number,
                    district_name,
                    address.build_map_tiler,
                    address.longitude,
                    address.latitude,
                    adverts.published_at,
                    agency.agency_catalog,
                    property_building.square,
                    property_building.floor,
                    property_building.room,
                    property_building.total_price,
                    img.image_url""", nativeQuery = true)
    List<MapAdvertsResponse> getAdvertsOnMap(@Param("addressId") Long addressId);
}

