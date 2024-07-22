package org.ui.main.advert.service;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.ui.main.advert.dto.CreateAdvertRequest;
import org.ui.main.advert.dto.FinalPageResponse;
import org.ui.main.advert.model.*;
import org.ui.main.advert.model.enums.Advantage;
import org.ui.main.advert.model.enums.ApartmentFeature;
import org.ui.main.advert.model.enums.Status;
import org.ui.main.advert.model.enums.TypeOwner;
import org.ui.main.advert.repository.*;
import org.ui.main.exceptions.NotFondException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final AdvantageRepository advantageRepository;
    private final FeatureRepository featureRepository;
    private final AddressRepository addressRepository;
    private final SellerRepository sellerRepository;
    private final AgencyRepository agencyRepository;
    private final PropertyRealtyRepository propertyRealtyRepository;

    public AdvertService(AdvertRepository advertRepository, AdvantageRepository advantageRepository, FeatureRepository featureRepository, AddressRepository addressRepository, SellerRepository sellerRepository, AgencyRepository agencyRepository, PropertyRealtyRepository propertyRealtyRepository) {
        this.advertRepository = advertRepository;
        this.advantageRepository = advantageRepository;
        this.featureRepository = featureRepository;
        this.addressRepository = addressRepository;
        this.sellerRepository = sellerRepository;
        this.agencyRepository = agencyRepository;
        this.propertyRealtyRepository = propertyRealtyRepository;
    }


    public FinalPageResponse findAdvertById(Long id) throws NotFondException {
        return advertRepository.getAdvertById(id)
                .orElseThrow(() -> new NotFondException(String.format("Advert with id %s not found", id)));
    }
    @Transactional
    public void createAdvert(CreateAdvertRequest request) {
        Advert advert = getAdvert(request);
        advertRepository.save(advert);
    }

    private Advert getAdvert(CreateAdvertRequest request) {
        Advert advert = new Advert();
        advert.setDescription(request.description());
        advert.setImages(request.images());
        advert.setEditedAt(LocalDateTime.now());
        advert.setPublishedAt(LocalDateTime.now());
        advert.setFinalUrl(request.finalUrl());
        advert.setStatus(Status.IN_USE);
        advert.setTypeRealty(request.typeRealty());
        advert.setAddress(getAddress(request));
        advert.setPropertyRealty(getProperty(request));
        advert.setSeller(getSeller(request));
        return advert;
    }

    private Seller getSeller(CreateAdvertRequest request) {

        Seller seller = new Seller();
        Optional<Seller> sellerInDb = sellerRepository.findByNameOwnerIgnoreCase(request.seller().nameOwner());
        if (sellerInDb.isPresent()) {
            return sellerInDb.get();
        }
        seller.setNameOwner(request.seller().nameOwner());
        seller.setNumberPhone(request.seller().numberPhone());
        seller.setTypeOwner(request.seller().typeOwner());

        if (!request.seller().typeOwner().name().equals(TypeOwner.OWNER.name())) {
            Optional<Agency> agencyInDb = agencyRepository.getAgencyByAgencyNameIsIgnoreCase(request.seller().agencyName());
            if (agencyInDb.isPresent()) {
                seller.setAgency(agencyInDb.get());
                return seller;
            }
            Agency agency = new Agency();
            agency.setAgencyName(request.seller().agencyName());
            Agency newAgency = agencyRepository.save(agency);
            seller.setAgency(newAgency);
        }
        return sellerRepository.save(seller);
    }

    private Address getAddress(CreateAdvertRequest request) {
        Address address = new Address();

        Optional<Address> addressInDb = addressRepository.findAddressByAddressNameAndDistrictAndCity(
                request.address().addressName(),
                request.address().district(),
                request.address().city());

        if (addressInDb.isPresent()) {
            return addressInDb.get();
        }

        address.setAddressName(request.address().addressName());
        address.setCity(request.address().city());
        address.setDistrict(request.address().district());
        address.setLatitude(request.address().latitude());
        address.setLongitude(request.address().longitude());
        address.setBuildIdMapTiler(request.address().buildIdMapTiler());

        return addressRepository.save(address);
    }

    private PropertyRealty getProperty(CreateAdvertRequest request) {
        PropertyRealty propertyRealty = new PropertyRealty();
        propertyRealty.setSquare(request.property().square());
        propertyRealty.setFloor(request.property().floor());
        propertyRealty.setRoom(request.property().room());
        propertyRealty.setRealtyPrice(request.property().realtyPrice());
        propertyRealty.setEnergyPrice(request.property().energyPrice());
        propertyRealty.setTotalPrice(request.property().totalPrice());
        propertyRealty.setWithKids(request.property().withKids());
        propertyRealty.setWithPets(request.property().withPets());


        Set<Advantage> advantages = request.property().advantages();
        List<Advantages> advantagesFromTable = advantageRepository.findAdvantagesByAdvantageNameIn(advantages);

        Set<ApartmentFeature> features = request.property().features();
        List<Features> featuresFromTable = featureRepository.findFeaturesByFeatureNameIn(features);

        propertyRealty.setAdvantageList(advantagesFromTable);
        propertyRealty.setFeatures(featuresFromTable);

        return propertyRealtyRepository.save(propertyRealty);
    }
}
