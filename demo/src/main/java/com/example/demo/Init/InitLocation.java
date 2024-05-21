package com.example.demo.Init;

import com.example.demo.model.locationModel;
import com.example.demo.repo.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InitLocation implements CommandLineRunner {

    private final LocationRepository locationRepository;

    public InitLocation(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initLocations();
    }

    private void initLocations() {
        if (locationRepository.count() == 0) {
            var location1 = new locationModel();
            location1.setName_location("Корпус 1");
            locationRepository.save(location1);

            var location2 = new locationModel();
            location2.setName_location("Корпус 2");
            locationRepository.save(location2);

        }
    }
}
