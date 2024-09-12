package org.ui.main.services.address.service;

import org.springframework.stereotype.Service;
import org.ui.main.services.address.dto.AllLocationDto;
import org.ui.main.services.address.dto.AllLocationResponse;
import org.ui.main.services.address.repository.AddressRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
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
}
