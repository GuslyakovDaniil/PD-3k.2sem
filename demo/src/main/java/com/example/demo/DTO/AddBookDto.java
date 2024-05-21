package com.example.demo.DTO;

import com.example.demo.Unique.UniqueNameTitle;
import com.example.demo.model.bookModel;

import java.util.List;

public class AddBookDto {

    private int number;
    private String title;
    private String author;
    private String genre;
    private String description;
    private boolean level;
    private int id;
    private int locationId;
    private List<bookModel> borrowedBooks;

    public List<bookModel> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<bookModel> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLevel() {
        return level;
    }

    public void setLevel(boolean level) {
        this.level = level;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public AddBookDto (){}
    public AddBookDto (int number,String title,String author,String genre,String description,boolean level){
        this.number = number;
        this.author = author;
        this.genre = genre;
        this.description = description;
        this.level = level;
        this.title = title;
    }
}
