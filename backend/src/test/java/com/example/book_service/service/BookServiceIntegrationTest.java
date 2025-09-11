package com.example.book_service.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.example.book_service.TestcontainersConfiguration;
import com.example.book_service.dto.BookDto;
import com.example.book_service.dto.BookLightweightDto;
import com.example.book_service.util.DtoUtil;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@Transactional
class BookServiceIntegrationTest {
    
    @Autowired
    private BookService service;

    private static final UUID BOOK_UUID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1");
    
    private static final String TITLE = "Harry Potter and the Philosopher's Stone";
    
    private static final LocalDate PUBLICATION_DATE = LocalDate.of(1997, 6, 26);
    
    private static final String SUMMARY = "First book in the Harry Potter series.";
    
    private static final Integer PAGE_COUNT = Integer.parseInt("223");

    private static final String AUTHOR_NAME = "J.K. Rowling";

    @Test
    void bookServiceIt_dbCommunicationShouldWorkAsExpected() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<BookLightweightDto> page = service.findAll(pageable);
        assertEquals(5, page.getContent().size()); 
        assertEquals(24, page.getTotalElements());



        BookDto foundById = service.findById(BOOK_UUID);

        assertEquals(TITLE, foundById.getTitle());
        assertEquals(PUBLICATION_DATE, foundById.getPublicationDate());
        assertEquals(SUMMARY, foundById.getSummary());
        assertEquals(PAGE_COUNT, foundById.getPageCount());
        assertEquals(AUTHOR_NAME, foundById.getAuthors().get(0).getName());

        Page<BookLightweightDto> foundByAuthorName = service.findByAuthorName(AUTHOR_NAME, pageable);
        assertEquals(2, foundByAuthorName.getContent().size());

        Page<BookLightweightDto> foundByTitle = service.findByTitle(TITLE, pageable);
        assertEquals(TITLE, foundByTitle.getContent().getFirst().getTitle());
        assertEquals(AUTHOR_NAME, foundByTitle.getContent().getFirst().getAuthors().get(0).getName());

        BookDto bookToSave = DtoUtil.buildBookDtoWithoutId();
        DtoUtil.addAuthors(bookToSave);
        BookLightweightDto savedBook = service.createOrUpdateBook(bookToSave);
        assertEquals(bookToSave.getTitle(), savedBook.getTitle());
        assertEquals(bookToSave.getAuthors().get(0).getName(), savedBook.getAuthors().get(0).getName());

        assertTrue(service.existsById(savedBook.getId()));

        assertEquals(25, service.findAll(pageable).getTotalElements());

        service.deleteById(savedBook.getId());

        assertEquals(24, service.findAll(pageable).getTotalElements());
    }

}
