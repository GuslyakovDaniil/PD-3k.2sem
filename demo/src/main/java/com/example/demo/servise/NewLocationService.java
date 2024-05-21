package com.example.demo.servise;

import com.example.demo.DTO.AddLocationDto;
import com.example.demo.model.locationModel;
import com.example.demo.repo.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewLocationService {
    private final LocationRepository locationRepository;
    private final ModelMapper mapper;

    public NewLocationService(LocationRepository locationRepository, ModelMapper mapper) {
        this.locationRepository = locationRepository;
        this.mapper = mapper;
    }

    public void addLocation(AddLocationDto addLocationDto) {
        locationRepository.saveAndFlush(mapper.map(addLocationDto, locationModel.class));
    }

    public List<AddLocationDto> allLocations() {
        return locationRepository.findAll().stream()
                .map(location -> mapper.map(location, AddLocationDto.class))
                .collect(Collectors.toList());
    }

    public AddLocationDto getLocationDetails(int id) {
        return mapper.map(locationRepository.findById(id).orElse(null), AddLocationDto.class);
    }

    public void removeLocation(int id) {
        locationRepository.deleteById(id);
    }
}
