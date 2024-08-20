package org.ui.main.services.advert.service;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ui.main.services.address.dto.CreateAdvertRequest;
import org.ui.main.services.address.model.Address;
import org.ui.main.services.address.service.AddressService;
import org.ui.main.services.advert.Converter;
import org.ui.main.services.advert.dto.AdminResponse;
import org.ui.main.services.advert.dto.FinalPageResponse;
import org.ui.main.services.advert.model.enums.Advantage;
import org.ui.main.services.advert.model.enums.ApartmentFeature;
import org.ui.main.services.advert.model.property.Advantages;
import org.ui.main.services.advert.model.property.Features;
import org.ui.main.services.advert.model.property.PropertyRealty;
import org.ui.main.services.advert.model.Advert;
import org.ui.main.services.advert.repository.AdvertRepository;
import org.ui.main.services.advert.repository.property.AdvantageRepository;
import org.ui.main.services.advert.repository.property.FeatureRepository;
import org.ui.main.services.advert.repository.property.PropertyRealtyRepository;
import org.ui.main.services.seller.model.Seller;
import org.ui.main.services.seller.repository.AgencyRepository;
import org.ui.main.services.seller.repository.SellerRepository;
import org.ui.main.exceptions.NotFondException;

import java.util.HashSet;
import java.util.List;
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
    private final RestTemplate restTemplate;


    public AdvertService(AdvertRepository advertRepository, AdvantageRepository advantageRepository, FeatureRepository featureRepository, AddressService addressService, SellerRepository sellerRepository, AgencyRepository agencyRepository, PropertyRealtyRepository propertyRealtyRepository, RestTemplate restTemplate) {
        this.advertRepository = advertRepository;
        this.advantageRepository = advantageRepository;
        this.featureRepository = featureRepository;
        this.addressService = addressService;
        this.sellerRepository = sellerRepository;
        this.agencyRepository = agencyRepository;
        this.propertyRealtyRepository = propertyRealtyRepository;
        this.restTemplate = restTemplate;
    }


    public FinalPageResponse findAdvertById(Long id) throws NotFondException {
        return advertRepository.getAdvertById(id)
                .orElseThrow(() -> new NotFondException(String.format("Advert with id %s not found", id)));
    }

    @Transactional
    public void createAdvert(CreateAdvertRequest request) {
        Advert advert = getAdvert(request);
        Advert savedAdvert = advertRepository.save(advert);
        if(request.idApplication()!=null) {
            AdminResponse completed = new AdminResponse(
                    savedAdvert.getId(),
                    request.idApplication(),
                    savedAdvert.getFinalUrl(),
                    "COMPLETED");
            restTemplate.postForEntity("https://localhost:9190/api/status", completed, AdminResponse.class);
        }
    }

    private Advert getAdvert(CreateAdvertRequest request) {
        Address address = addressService.createAddress(request.address());
        HashSet<String> images = new HashSet<>();
        PropertyRealty property = getProperty(request);
        Seller seller = sellerRepository.findBySellerAuthId(request.sellerAuthId()).get();
        return Converter.toAdvert(request, images, property, address, seller);
    }

//    private Seller getSeller(CreateAdvertRequest request) {
//
//        Seller seller = new Seller();
//        Optional<Seller> sellerInDb = sellerRepository.findByNameOwnerIgnoreCase(request.seller().nameOwner());
//        if (sellerInDb.isPresent()) {
//            return sellerInDb.get();
//        }
//        seller.setNameOwner(request.seller().nameOwner());
//        seller.setNumberPhone(request.seller().numberPhone());
//        seller.setTypeOwner(request.seller().typeOwner());
//
//        if (!request.seller().typeOwner().name().equals(TypeOwner.OWNER.name())) {
//            Optional<Agency> agencyInDb = agencyRepository.getAgencyByAgencyNameIsIgnoreCase(request.seller().agencyName());
//            if (agencyInDb.isPresent()) {
//                seller.setAgency(agencyInDb.get());
//                return sellerRepository.save(seller);
//            }
//            Agency agency = new Agency();
//            agency.setAgencyName(request.seller().agencyName());
//            agency.setAgencyCatalog("/");
//            Agency newAgency = agencyRepository.save(agency);
//            seller.setAgency(newAgency);
//        }
//        return sellerRepository.save(seller);
//    }


    private PropertyRealty getProperty(CreateAdvertRequest request) {
        Set<Advantage> advantages = request.property().advantages();
        List<Advantages> advantagesFromTable = advantageRepository.findAdvantagesByAdvantageNameIn(advantages);
        Set<ApartmentFeature> features = request.property().features();
        List<Features> featuresFromTable = featureRepository.findFeaturesByFeatureNameIn(features);
        PropertyRealty propertyRealty = Converter.toPropertyRealty(request, advantagesFromTable, featuresFromTable);
        return propertyRealtyRepository.save(propertyRealty);
    }
}
