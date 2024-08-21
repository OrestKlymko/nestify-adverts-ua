package org.ui.main.services.advert.service;


import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ui.main.services.address.dto.CreateAdvertRequest;
import org.ui.main.services.address.model.Address;
import org.ui.main.services.address.service.AddressService;
import org.ui.main.services.advert.Converter;
import org.ui.main.services.advert.dto.AdminResponse;
import org.ui.main.services.advert.dto.AdvertInfoResponse;
import org.ui.main.services.advert.dto.FinalPageResponse;
import org.ui.main.services.advert.dto.SellerResponse;
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
import org.ui.main.exceptions.NotFondException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdvertService {

    private static final Logger log = LoggerFactory.getLogger(AdvertService.class);
    @Value("${nestify-admin-service-url}")
    private String adminServiceUrl;

    @Value("${nestify-auth-service-url}")
    private String authServiceUrl;

    private final AdvertRepository advertRepository;
    private final AdvantageRepository advantageRepository;
    private final FeatureRepository featureRepository;
    private final AddressService addressService;
    private final PropertyRealtyRepository propertyRealtyRepository;
    private final RestTemplate restTemplate;


    public AdvertService(AdvertRepository advertRepository, AdvantageRepository advantageRepository, FeatureRepository featureRepository, AddressService addressService, PropertyRealtyRepository propertyRealtyRepository, RestTemplate restTemplate) {
        this.advertRepository = advertRepository;
        this.advantageRepository = advantageRepository;
        this.featureRepository = featureRepository;
        this.addressService = addressService;
        this.propertyRealtyRepository = propertyRealtyRepository;
        this.restTemplate = restTemplate;
    }


    public FinalPageResponse findAdvertById(Long id) throws NotFondException {
        Optional<AdvertInfoResponse> advertById = advertRepository.getAdvertById(id);
        if (advertById.isPresent()) {
            SellerResponse seller = getSeller(advertById.get().getSellerId());
            return new FinalPageResponse(advertById.get(), seller);
        }
        throw new NotFondException(String.format("Advert with id %s not found", id));
    }


    private SellerResponse getSeller(String sellerId) throws NotFondException {
        ResponseEntity<SellerResponse> response = restTemplate
                .getForEntity(authServiceUrl + "/api/seller/" + sellerId, SellerResponse.class);
        if (response.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return response.getBody();
        } else {
            log.error("Seller with id {} not found", sellerId);
            throw new NotFondException(String.format("Seller with id %s not found", sellerId));
        }

    }

    @Transactional
    public void createAdvert(CreateAdvertRequest request) {
        Advert advert = getAdvert(request);
        Advert savedAdvert = advertRepository.save(advert);
        if (request.idApplication() != null) {
            sendNotificationToAdmin(savedAdvert);
        }
    }

    private void sendNotificationToAdmin(Advert savedAdvert) {
        AdminResponse completed = new AdminResponse(
                savedAdvert.getId(),
                savedAdvert.getApplicationId(),
                savedAdvert.getFinalUrl(),
                "COMPLETED");
        restTemplate.postForEntity(adminServiceUrl + "/api/status", completed, AdminResponse.class);
    }

    private Advert getAdvert(CreateAdvertRequest request) {

        Address address = addressService.createAddress(request.address());
        HashSet<String> images = new HashSet<>();
        PropertyRealty property = getProperty(request);

        return Converter.toAdvert(request, images, property, address);
    }


    private PropertyRealty getProperty(CreateAdvertRequest request) {
        Set<Advantage> advantages = request.property().advantages();
        List<Advantages> advantagesFromTable = advantageRepository.findAdvantagesByAdvantageNameIn(advantages);
        Set<ApartmentFeature> features = request.property().features();
        List<Features> featuresFromTable = featureRepository.findFeaturesByFeatureNameIn(features);
        PropertyRealty propertyRealty = Converter.toPropertyRealty(request, advantagesFromTable, featuresFromTable);
        return propertyRealtyRepository.save(propertyRealty);
    }
}
