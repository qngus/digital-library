package com.example.book_service.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_service.dto.BookDto;
import com.example.book_service.dto.BookLightweightDto;
import com.example.book_service.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {
    
    private final BookService service;

    @GetMapping
    public Page<BookLightweightDto> getBooks(@RequestParam(required = false) String author, @RequestParam(required = false) String title, Pageable pageable) {
        if (author != null && title != null) {
            return service.findByAuthorNameAndTitle(author, title, pageable);
        }
        if (author != null) {
            return service.findByAuthorName(author, pageable);
        }
        if (title != null) {
            return service.findByTitle(title, pageable);
        }
        return service.findAll(pageable);
    }
    

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public BookLightweightDto createBook(@Valid @RequestBody BookDto book) {
        return service.createOrUpdateBook(book);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public BookLightweightDto updateBook(@Valid @RequestBody BookDto book) {
        return service.createOrUpdateBook(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
       if (service.existsById(id).booleanValue()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
