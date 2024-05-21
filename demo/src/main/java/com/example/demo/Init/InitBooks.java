package com.example.demo.Init;

import com.example.demo.model.bookModel;
import com.example.demo.model.locationModel;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitBooks implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final LocationRepository locationRepository;

    public InitBooks(BookRepository bookRepository, LocationRepository locationRepository) {
        this.bookRepository = bookRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initBooks();
    }

    private void initBooks() {
        if (bookRepository.count() == 0) {
            bookRepository.saveAll(getInitialBooks());
        }
    }

    private List<bookModel> getInitialBooks() {
        locationModel location1 = locationRepository.findByName_location("Корпус 1");
        locationModel location2 = locationRepository.findByName_location("Корпус 2");

        if (location1 == null || location2 == null) {
            throw new RuntimeException("Местоположения не найдены в базе данных.");
        }

        return List.of(
                new bookModel("Название книги 1", location1, "Автор книги 1", "Жанр книги 1", "Описание книги 1", true, 1),
                new bookModel("Название книги 2", location2, "Автор книги 2", "Жанр книги 2", "Описание книги 2", true, 200),
                new bookModel("Название книги 3", location1, "Автор книги 3", "Жанр книги 3", "Описание книги 3", true, 1),
                new bookModel("Название книги 4", location2, "Автор книги 4", "Жанр книги 4", "Описание книги 4", true, 400)
        );
    }
}
