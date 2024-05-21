package com.example.demo.repo;

import com.example.demo.model.locationModel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<locationModel, Integer> {
    @Query("SELECT l FROM locationModel l WHERE l.name_location = :name_location")
    locationModel findByName_location(@Param("name_location") String name_location);
}
