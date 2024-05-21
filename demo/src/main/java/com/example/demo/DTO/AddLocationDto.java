package com.example.demo.DTO;

public class AddLocationDto {
    public String getName_location() {
        return name_location;
    }

    public void setName_location(String name_location) {
        this.name_location = name_location;
    }

    private String name_location;

    public AddLocationDto(){}
    public AddLocationDto(String name_location){
        this.name_location = name_location;
    }
}
