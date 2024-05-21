package com.example.demo.Control;

import com.example.demo.DTO.AddBookDto;
import com.example.demo.model.bookModel;
import com.example.demo.model.locationModel;
import com.example.demo.servise.NewBookService;
import com.example.demo.repo.LocationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
public class BookController {
    private final NewBookService bookService;
    private final LocationRepository locationRepository;

    public BookController(NewBookService bookService, LocationRepository locationRepository) {
        this.bookService = bookService;
        this.locationRepository = locationRepository;
    }



    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        List<locationModel> locations = locationRepository.findAll();
        model.addAttribute("locations", locations);
        return "book-add";
    }

    @GetMapping("/")
    public String showBookList(Model model) {
        List<AddBookDto> books = bookService.allBooks();
        model.addAttribute("books", books);
        return "index";
    }


    @PostMapping("/books/add")
    public ResponseEntity<Void> addBook(@ModelAttribute AddBookDto addBookDto, @RequestParam("location_id") Integer locationId) {
        addBookDto.setLocationId(locationId);
        bookService.addBook(addBookDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/books/search")
    public String searchBooks(@RequestParam("search") String search, Model model) {
        List<AddBookDto> books = bookService.searchBooks(search);
        model.addAttribute("books", books);
        return "index";
    }
    @GetMapping("/books/titles")
    public ResponseEntity<List<String>> getBookTitles(@RequestParam("search") String search) {
        List<String> titles = bookService.getBookTitlesBySearchTerm(search);
        return ResponseEntity.ok(titles);
    }

}
