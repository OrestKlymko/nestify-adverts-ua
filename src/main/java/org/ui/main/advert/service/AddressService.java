package org.ui.main.advert.service;


import org.springframework.stereotype.Service;
import org.ui.main.advert.dto.AddressCreateRequest;
import org.ui.main.advert.dto.AllLocationDto;
import org.ui.main.advert.dto.AllLocationResponse;
import org.ui.main.advert.model.address.Address;
import org.ui.main.advert.model.address.City;
import org.ui.main.advert.model.address.District;
import org.ui.main.advert.model.address.Street;
import org.ui.main.advert.repository.address.AddressRepository;
import org.ui.main.advert.repository.address.CityRepository;
import org.ui.main.advert.repository.address.DistrictRepository;
import org.ui.main.advert.repository.address.StreetRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final StreetRepository streetRepository;

    public AddressService(AddressRepository addressRepository, CityRepository cityRepository, DistrictRepository districtRepository, StreetRepository streetRepository) {
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.streetRepository = streetRepository;
    }


    public List<AllLocationResponse> getAllLocation() {
        AtomicInteger id = new AtomicInteger();
        List<AllLocationDto> allLocation = addressRepository.getAllLocation();

        List<AllLocationResponse> locationResponses = new ArrayList<>();

        Set<String> cities = new HashSet<>();
        Map<String, String> districts = new HashMap<>();

        allLocation.forEach(location -> {
            locationResponses.add(new AllLocationResponse(id.get(), location.getCity(), location.getDistrict(), location.getStreet()));
            id.getAndIncrement();
            cities.add(location.getCity());
            districts.put(location.getDistrict(), location.getCity());
        });

        cities.forEach(city -> {
            locationResponses.add(new AllLocationResponse(id.get(),city, "", ""));
            id.getAndIncrement();
        });

        districts.forEach((district, city) -> {
            locationResponses.add(new AllLocationResponse(id.get(),city, district, ""));
            id.getAndIncrement();
        });

        return locationResponses;
    }


    public Address createAddress(AddressCreateRequest request) {
        City city = getCity(request.city());
        District district = getDistrict(request.district(), request.city());
        Street street = getStreet(request.street(), request.houseNumber(), request.city(), request.district());

        Address address = new Address();
        address.setBuildIdMapTiler(request.buildIdMapTiler());
        address.setLatitude(request.latitude());
        address.setLongitude(request.longitude());
        address.setBuildIdMapTiler(request.buildIdMapTiler());
        address.setCity(city);
        address.setDistrict(district);
        address.setStreet(street);

        return addressRepository.save(address);
    }

    private Street getStreet(String streetName, Integer numberHouse, String city, String districtName) {
        Optional<Street> street = streetRepository.findStreet(streetName, numberHouse, districtName, city);
        if (street.isPresent()) return street.get();
        District district = getDistrict(districtName, city);
        Street streetToSave = new Street();
        streetToSave.setStreetName(streetName);
        streetToSave.setStreetNumber(numberHouse);
        streetToSave.setDistrict(district);

        return streetRepository.save(streetToSave);

    }

    private District getDistrict(String districtName, String cityName) {
        Optional<District> district = districtRepository.getDistrictByDistrictName(districtName);

        if (district.isPresent()) return district.get();
        City city = getCity(cityName);

        District districtToSave = new District();
        districtToSave.setDistrictName(districtName);
        districtToSave.setCity(city);
        return districtRepository.save(districtToSave);
    }

    private City getCity(String cityName) {
        Optional<City> city = cityRepository.getCityByCityName(cityName);

        if (city.isPresent()) return city.get();

        City cityToSave = new City();
        cityToSave.setCityName(cityName);
        return cityRepository.save(cityToSave);

    }
}
