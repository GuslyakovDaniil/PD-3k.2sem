package com.example.demo.Control;

import com.example.demo.DTO.AddLocationDto;
import com.example.demo.servise.NewLocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {
    private final NewLocationService locationService;

    public LocationController(NewLocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addLocation(@RequestBody AddLocationDto addLocationDto) {
        locationService.addLocation(addLocationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddLocationDto>> allLocations() {
        List<AddLocationDto> locations = locationService.allLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<AddLocationDto> getLocationDetails(@PathVariable int id) {
        AddLocationDto locationDetails = locationService.getLocationDetails(id);
        return ResponseEntity.ok(locationDetails);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeLocation(@PathVariable int id) {
        locationService.removeLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
