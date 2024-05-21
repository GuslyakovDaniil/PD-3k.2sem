package com.example.demo.model;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name="locations")
public class locationModel extends BaseEntity{
    private String name_location;

    @Column(name = "name_location")
    public String getName_location() {
        return name_location;
    }

    public void setName_location(String name_location) {
        this.name_location = name_location;
    }

    public locationModel(){}
}
