package org.ui.main.services.map.contoller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.ui.main.services.map.dto.MapAdvertsResponse;
import org.ui.main.services.map.dto.PointMapResponse;
import org.ui.main.services.map.service.MapService;
import org.ui.main.input.osm.dto.LocationInfoResponse;

import java.util.List;

@RestController
@RequestMapping("/api/map")
@Tag(name = "Map")
public class MapController {
    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    @Operation(summary = "Get all points of advert by id. Used for displaying on map in view window")
    public List<PointMapResponse> getAdvertsOnMap(@RequestParam List<Long> adverts) {
        return mapService.getAdvertByIds(adverts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get advert on map by Id")
    public List<MapAdvertsResponse> getAdvertOnMap(@PathVariable Long id) {
        return mapService.getAdvertOnMap(id);
    }

    @GetMapping("/coordinates/{location}")
    @Operation(summary = "Get coordinate on address. Keep your address maximum information")
    public LocationInfoResponse getCoordinates(@PathVariable String location) {
        return mapService.getCoordinates(location);
    }


}
