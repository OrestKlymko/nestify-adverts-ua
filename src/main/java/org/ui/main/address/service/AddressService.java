package org.ui.main.address.service;


import org.springframework.stereotype.Service;
import org.ui.main.address.dto.AddressCreateRequest;
import org.ui.main.address.dto.AllLocationDto;
import org.ui.main.address.dto.AllLocationResponse;
import org.ui.main.address.model.Address;
import org.ui.main.address.model.City;
import org.ui.main.address.model.District;
import org.ui.main.address.model.Street;
import org.ui.main.address.repository.AddressRepository;
import org.ui.main.address.repository.CityRepository;
import org.ui.main.address.repository.DistrictRepository;
import org.ui.main.address.repository.StreetRepository;
import org.ui.main.osm.dto.LocationInfoResponse;
import org.ui.main.osm.service.OsmService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final StreetRepository streetRepository;
    private final OsmService osmService;

    public AddressService(AddressRepository addressRepository, CityRepository cityRepository, DistrictRepository districtRepository, StreetRepository streetRepository, OsmService osmService) {
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.streetRepository = streetRepository;
        this.osmService = osmService;
    }


    public List<AllLocationResponse> getAllLocation() {
        AtomicInteger id = new AtomicInteger();
        List<AllLocationDto> allLocation = addressRepository.getAllLocation();

        List<AllLocationResponse> locationResponses = new ArrayList<>();

        Set<String> cities = new HashSet<>();
        Map<String, String> districts = new HashMap<>();

        allLocation.forEach(location -> {
            cities.add(location.getCity());
            districts.put(location.getDistrict(), location.getCity());
        });

        cities.forEach(city -> {
            locationResponses.add(new AllLocationResponse(id.get(), city, "", ""));
            id.getAndIncrement();
        });

        districts.forEach((district, city) -> {
            locationResponses.add(new AllLocationResponse(id.get(), city, district, ""));
            id.getAndIncrement();
        });

        allLocation.forEach(location -> {
            locationResponses.add(new AllLocationResponse(id.get(), location.getCity(), location.getDistrict(), location.getStreet()));
            id.getAndIncrement();
        });

        return locationResponses;
    }


    public Address createAddress(AddressCreateRequest request) {
        City city = getCity(request.city());
        District district = getDistrict(request.district(), request.city());
        Street street = getStreet(request.street(), request.houseNumber(), request.city(), request.district());
        LocationInfoResponse coordinatesFromOSM = osmService.getCoordinatesFromOSM(request.city() + "," + request.street() + "," + request.houseNumber());
        Address address = new Address();
        address.setBuildIdMapTiler(request.buildIdMapTiler());
        address.setLatitude(coordinatesFromOSM.latitude());
        address.setLongitude(coordinatesFromOSM.longitude());
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
