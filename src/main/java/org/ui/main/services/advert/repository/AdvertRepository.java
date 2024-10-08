package org.ui.main.services.advert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ui.main.services.advert.dto.AdvertInfoResponse;
import org.ui.main.services.advert.model.Advert;

import java.util.Optional;


@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {
    @Query(value = """
            SELECT adverts.id                                             as id,
                   adverts.description                                    as description,
                   adverts.final_url                                      as finalUrl,
                   adverts.type_realty                                    as typeRealty,
                   adverts.seller_id                                      as sellerId,
                   city.city_name                                         as city,
                   districts.district_name                                as district,
                   concat(street.street_name, ', ', street.street_number) as address,
                   address.build_map_tiler                                as buildIdMapTiler,
                   address.longitude                                      as longitude,
                   address.latitude                                       as latitude,
                   property_building.id                                   as propertyId,
                   property_building.square                               as square,
                   property_building.floor                                as floor,
                   property_building.room                                 as room,
                   property_building.realty_price                         as realtyPrice,
                   property_building.total_price                          as totalPrice,
                   property_building.with_kids                            as withKids,
                   property_building.with_pets                            as withPets,
                   ARRAY_AGG(DISTINCT images.image_url)                   as images,
                   ARRAY_AGG(DISTINCT features.feature_value_ua)          as features,
                   adverts.parser_owner_name                              as parserOwnerName,
                   adverts.type_owner                                     as typeOwner
            FROM adverts
                     LEFT JOIN address ON adverts.address_id = address.id
                     LEFT JOIN city ON address.city_id = city.id
                     LEFT JOIN districts ON address.district_id = districts.id
                     LEFT JOIN street ON address.street_id = street.id
                     LEFT JOIN property_building ON adverts.property_id = property_building.id
                     LEFT JOIN images on adverts.id = images.advert_id
                     LEFT JOIN property_features pf on property_building.id = pf.property_id
                     LEFT JOIN features on pf.feature_id = features.id
            WHERE adverts.id = :id
            GROUP BY adverts.id,
                     adverts.description,
                     adverts.final_url,
                     adverts.type_realty,
                     adverts.seller_id,
                     city.city_name,
                     districts.district_name,
                     street.street_number,
                     street.street_name,
                     address.build_map_tiler,
                     address.longitude,
                     address.latitude,
                     property_building.id,
                     property_building.square,
                     property_building.floor,
                     property_building.room,
                     property_building.realty_price,
                     property_building.total_price,
                     property_building.with_kids,
                     property_building.with_pets
           """, nativeQuery = true)
    Optional<AdvertInfoResponse> getAdvertById(@Param("id") Long id);
}
