package org.ui.main.map.contoller;


import org.springframework.web.bind.annotation.*;
import org.ui.main.map.dto.MapAdvertsResponse;
import org.ui.main.map.dto.PointMapResponse;
import org.ui.main.map.service.MapService;
import org.ui.main.osm.dto.LocationInfoResponse;

import java.util.List;

@RestController
@RequestMapping("/api/map")
public class MapController {
    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public List<PointMapResponse> getAdvertsOnMap(@RequestParam List<Long> adverts) {
        return mapService.getAdvertByIds(adverts);
    }

    @GetMapping("/{id}")
    public List<MapAdvertsResponse> getAdvertOnMap(@PathVariable Long id) {
        return mapService.getAdvertOnMap(id);
    }

    @GetMapping("/coordinates/{location}")
    public LocationInfoResponse getCoordinates(@PathVariable String location) {
        return mapService.getCoordinates(location);
    }


}
