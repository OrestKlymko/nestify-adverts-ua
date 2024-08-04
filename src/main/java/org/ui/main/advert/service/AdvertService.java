package org.ui.main.advert.service;


import org.springframework.stereotype.Service;
import org.ui.main.address.dto.CreateAdvertRequest;
import org.ui.main.address.service.AddressService;
import org.ui.main.advert.dto.FinalPageResponse;
import org.ui.main.advert.model.*;
import org.ui.main.advert.model.enums.Advantage;
import org.ui.main.advert.model.enums.ApartmentFeature;
import org.ui.main.advert.model.enums.Status;
import org.ui.main.advert.model.enums.TypeOwner;
import org.ui.main.advert.model.property.Advantages;
import org.ui.main.advert.model.property.Features;
import org.ui.main.advert.model.property.PropertyRealty;
import org.ui.main.advert.model.seller.Agency;
import org.ui.main.advert.model.seller.Seller;
import org.ui.main.advert.repository.*;
import org.ui.main.advert.repository.property.AdvantageRepository;
import org.ui.main.advert.repository.property.FeatureRepository;
import org.ui.main.advert.repository.property.PropertyRealtyRepository;
import org.ui.main.advert.repository.seller.AgencyRepository;
import org.ui.main.advert.repository.seller.SellerRepository;
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
    private final AddressService addressService;
    private final SellerRepository sellerRepository;
    private final AgencyRepository agencyRepository;
    private final PropertyRealtyRepository propertyRealtyRepository;


    public AdvertService(AdvertRepository advertRepository, AdvantageRepository advantageRepository, FeatureRepository featureRepository, AddressService addressService, SellerRepository sellerRepository, AgencyRepository agencyRepository, PropertyRealtyRepository propertyRealtyRepository) {
        this.advertRepository = advertRepository;
        this.advantageRepository = advantageRepository;
        this.featureRepository = featureRepository;
        this.addressService = addressService;
        this.sellerRepository = sellerRepository;
        this.agencyRepository = agencyRepository;
        this.propertyRealtyRepository = propertyRealtyRepository;
    }


    public FinalPageResponse findAdvertById(Long id) throws NotFondException {
        return advertRepository.getAdvertById(id)
                .orElseThrow(() -> new NotFondException(String.format("Advert with id %s not found", id)));
    }


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
        advert.setAddress(addressService.createAddress(request.address()));
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
                return sellerRepository.save(seller);
            }
            Agency agency = new Agency();
            agency.setAgencyName(request.seller().agencyName());
            agency.setAgencyCatalog("/");
            Agency newAgency = agencyRepository.save(agency);
            seller.setAgency(newAgency);
        }
        return sellerRepository.save(seller);
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
