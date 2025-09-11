package com.example.book_service.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.book_service.dto.BookDto;
import com.example.book_service.dto.BookLightweightDto;
import com.example.book_service.entity.AuthorEntity;
import com.example.book_service.entity.BookEntity;
import com.example.book_service.exception.BookNotFoundException;
import com.example.book_service.mapper.BookMapper;
import com.example.book_service.repository.AuthorRepository;
import com.example.book_service.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final BookMapper mapper;

    public Page<BookLightweightDto> findAll(Pageable pageable) {
        return mapper.toLightweightDtoPage(bookRepository.findAll(pageable));
    }

    public BookDto findById(UUID id) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            return mapper.toDto(optionalBook.get());
        }
        throw new BookNotFoundException(id);
    }

    public Page<BookLightweightDto> findByAuthorName(String authorName, Pageable pageable) {
        return mapper.toLightweightDtoPage(bookRepository.findByAuthors_NameContainingIgnoreCase(authorName, pageable));
    }

    public Page<BookLightweightDto> findByTitle(String title, Pageable pageable) {
        return mapper.toLightweightDtoPage(bookRepository.findByTitleContainingIgnoreCase(title, pageable));
    }

    public Page<BookLightweightDto> findByAuthorNameAndTitle(String authorName, String title, Pageable pageable) {
        return mapper.toLightweightDtoPage(bookRepository.findAllByAuthorsNameAndTitleIgnoreCase(authorName, title, pageable));
    }

    @Transactional
    public BookLightweightDto createOrUpdateBook(BookDto bookDto) {
        BookEntity book = mapper.toEntity(bookDto);

        Set<AuthorEntity> resolvedAuthors = bookDto.getAuthors().stream()
            .map(authorDto -> authorRepository.findByName(authorDto.getName())
                .orElseGet(() -> {
                    AuthorEntity newAuthor = new AuthorEntity();
                    newAuthor.setName(authorDto.getName());
                    return authorRepository.save(newAuthor);
                })
            )
            .collect(Collectors.toSet());

        book.setAuthors(resolvedAuthors);
        return mapper.toBookLightweightDto(bookRepository.save(book));
    }

    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }

    public Boolean existsById(UUID id) {
        return bookRepository.existsById(id);
    }

}
