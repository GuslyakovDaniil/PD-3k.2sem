package com.example.demo.servise;

import com.example.demo.DTO.AddBookDto;
import com.example.demo.model.bookModel;
import com.example.demo.model.locationModel;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewBookService {
    private static BookRepository bookRepository;
    private final ModelMapper mapper;

    public NewBookService(BookRepository bookRepository,  ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public void addBook(AddBookDto addBookDto) {
        bookModel book = mapper.map(addBookDto, bookModel.class);
        bookRepository.saveAndFlush(book);
    }


    public List<AddBookDto> allBooks() {
        return bookRepository.findAll().stream()
                .map(book -> mapper.map(book, AddBookDto.class))
                .collect(Collectors.toList());
    }

    public AddBookDto bookDetails(int id) {
        return mapper.map(bookRepository.findById(id).orElse(null), AddBookDto.class);
    }

    public void removeBook(int id) {
        bookRepository.deleteById(id);
    }

    public static List<bookModel> getBorrowedBooksByUserId(int userId) {
        return bookRepository.findBorrowedBooksByUserId(userId);
    }
    public List<AddBookDto> searchBooks(String searchTerm) {
        return bookRepository.findByTitleContainingOrAuthorContaining(searchTerm, searchTerm).stream()
                .map(book -> mapper.map(book, AddBookDto.class))
                .collect(Collectors.toList());
    }
    public List<String> getBookTitlesBySearchTerm(String searchTerm) {
        return bookRepository.findByTitleContaining(searchTerm).stream()
                .map(book -> book.getTitle())
                .collect(Collectors.toList());
    }
}
