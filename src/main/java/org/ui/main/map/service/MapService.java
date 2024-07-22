package org.ui.main.map.service;


import org.springframework.stereotype.Service;
import org.ui.main.map.dto.MapAdvertsResponse;
import org.ui.main.map.dto.PointMapResponse;
import org.ui.main.map.repository.MapRepository;
import org.ui.main.osm.service.OsmService;
import org.ui.main.osm.dto.LocationInfoResponse;

import java.util.List;

@Service
public class MapService {
    private final MapRepository mapRepository;
    private final OsmService osmService;

    public MapService(MapRepository mapRepository, OsmService osmService) {
        this.mapRepository = mapRepository;
        this.osmService = osmService;
    }

    public List<MapAdvertsResponse> getAdvertOnMap(Long addressId){
        return mapRepository.getAdvertsOnMap(addressId);
    }

    public List<PointMapResponse> getAdvertByIds(List<Long> adverts) {
        return mapRepository.getPointsMap(adverts);
    }

    public LocationInfoResponse getCoordinates(String location) {
       return osmService.getCoordinatesFromOSM(location);
    }
}
