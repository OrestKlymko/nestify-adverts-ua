package org.ui.main.services.advert;

import org.ui.main.services.address.dto.CreateAdvertRequest;
import org.ui.main.services.address.model.Address;
import org.ui.main.services.advert.model.Advert;
import org.ui.main.services.advert.model.enums.Status;
import org.ui.main.services.advert.model.property.Advantages;
import org.ui.main.services.advert.model.property.Features;
import org.ui.main.services.advert.model.property.PropertyRealty;
import org.ui.main.services.seller.model.Seller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Converter {
    public static Advert toAdvert(CreateAdvertRequest request,
                                  Set<String> images,
                                  PropertyRealty realty,
                                  Address address,
                                  Seller seller) {
        Advert advert = new Advert();
        advert.setDescription(request.description());
        advert.setImages(images);
        advert.setEditedAt(LocalDateTime.now());
        advert.setPublishedAt(LocalDateTime.now());
        advert.setFinalUrl(request.finalUrl());
        advert.setStatus(Status.IN_USE);
        advert.setTypeRealty(request.typeRealty());
        advert.setAddress(address);
        advert.setPropertyRealty(realty);
        advert.setSeller(seller);
        return advert;
    }

    public static PropertyRealty toPropertyRealty(CreateAdvertRequest request, List<Advantages> advantagesFromTable, List<Features> featuresFromTable) {
        PropertyRealty propertyRealty = new PropertyRealty();
        propertyRealty.setMaxPeople(request.property().maxPerson());
        propertyRealty.setSquare(request.property().square());
        propertyRealty.setFloor(request.property().floor());
        propertyRealty.setRoom(request.property().room());
        propertyRealty.setRealtyPrice(request.property().realtyPrice());
        propertyRealty.setEnergyPrice(request.property().energyPrice());
        propertyRealty.setTotalPrice(request.property().totalPrice());
        propertyRealty.setWithKids(request.property().withKids());
        propertyRealty.setMaxPeople(request.property().maxPerson());
        propertyRealty.setWithPets(request.property().withPets());
        propertyRealty.setAdvantageList(advantagesFromTable);
        propertyRealty.setFeatures(featuresFromTable);
        return propertyRealty;
    }
}
