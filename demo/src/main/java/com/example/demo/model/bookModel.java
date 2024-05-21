package com.example.demo.model;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name="books")
public class bookModel extends BaseEntity{
    private String title;
    private String author;
    private String genre;
    private String description;
    private boolean level;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "location_id")
    private locationModel location;

    public locationModel getLocation() {
        return location;
    }

    public void setLocation(locationModel location) {
        this.location = location;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "genre")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "level")
    public boolean isLevel() {
        return level;
    }

    public void setLevel(boolean level) {
        this.level = level;
    }
    public bookModel(){}

    public bookModel(String title,locationModel location, String author, String genre, String description, boolean level, int number) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.description = description;
        this.level = level;
        this.number = number;
        this.location = location;
    }

}