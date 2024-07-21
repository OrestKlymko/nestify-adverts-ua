package org.ui.main.advert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ui.main.advert.dto.FinalPageResponse;
import org.ui.main.advert.model.Advert;

import java.util.Optional;


@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {
    @Query(value = """
            SELECT
                adverts.id as id,
                adverts.description as description,
                adverts.final_url as finalUrl,
                adverts.type_realty as typeRealty,
                seller.name_owner as nameOwner,
                seller.type_owner as typeOwner,
                seller.number_phone as numberPhone,
                address.city as city,
                address.district as district,
                address.address_name as address,
                address.build_map_tiler as buildIdMapTiler,
                address.longitude as longitude,
                address.latitude as latitude,
                agency.agency_name as agencyName,
                property_building.id as propertyId,
                property_building.square as square,
                property_building.floor as floor,
                property_building.room as room,
                property_building.realty_price as realtyPrice,
                property_building.total_price as totalPrice,
                property_building.with_kids as withKids,
                property_building.with_pets as withPets,
                ARRAY_AGG(DISTINCT images.image_url) as images,
                ARRAY_AGG(DISTINCT features.feature) as features,
                ARRAY_AGG(DISTINCT advantages.advantage) as advantages
            FROM adverts
            JOIN address ON adverts.address_id = address.id
            JOIN property_building ON adverts.property_id = property_building.id
            JOIN seller on adverts.seller_id = seller.id
            LEFT JOIN images on adverts.id = images.advert_id
            LEFT JOIN agency on seller.agency_id = agency.id
            LEFT JOIN features on property_building.id = features.property_id
            LEFT JOIN advantages on property_building.id = advantages.property_id
            WHERE adverts.id = :id
            GROUP BY
                adverts.id,
                adverts.description,
                adverts.final_url,
                adverts.type_realty,
                seller.name_owner,
                seller.type_owner,
                seller.number_phone,
                address.city,
                address.district,
                address.address_name,
                address.build_map_tiler,
                address.longitude,
                address.latitude,
                agency.agency_name,
                property_building.id,
                property_building.square,
                property_building.floor,
                property_building.room,
                property_building.realty_price,
                property_building.total_price,
                property_building.with_kids,
                property_building.with_pets
           """, nativeQuery = true)
    Optional<FinalPageResponse> getAdvertById(@Param("id") Long id);

}
