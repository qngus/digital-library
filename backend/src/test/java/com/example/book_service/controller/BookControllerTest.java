package com.example.book_service.controller;

import com.example.book_service.dto.BookDto;
import com.example.book_service.dto.BookLightweightDto;
import com.example.book_service.service.BookService;
import com.example.book_service.util.DtoUtil;
import com.example.book_service.util.TestUtil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService service;

    @Test
    @DisplayName("GET /api/books should return all books")
    void getAllBooks() throws Exception {
        BookLightweightDto bookDto = DtoUtil.buildBookLightweightDto();
        DtoUtil.addAuthors(bookDto);
        Page<BookLightweightDto> page = new PageImpl<>(List.of(bookDto), PageRequest.of(0, 2), 5);
        doReturn(page).when(service).findAll(any(Pageable.class));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value("How to Brew"))
                .andExpect(jsonPath("$.content[0].authors[0].name").value("John Palmer"));
    }

    @Test
    @DisplayName("GET /api/books?author= should filter by author")
    void getBooksByAuthor() throws Exception {
        BookLightweightDto bookDto = DtoUtil.buildBookLightweightDto();
        DtoUtil.addAuthors(bookDto);
        Page<BookLightweightDto> page = new PageImpl<>(List.of(bookDto), PageRequest.of(0, 2), 5);
        doReturn(page).when(service).findByAuthorName(eq("John Palmer"), any(Pageable.class));

        mockMvc.perform(get("/api/books").param("author", "John Palmer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value("How to Brew"))
                .andExpect(jsonPath("$.content[0].authors[0].name").value("John Palmer"));
    }

    @Test
    @DisplayName("GET /api/books?title= should filter by author")
    void getBooksByTitle() throws Exception {
        BookLightweightDto bookDto = DtoUtil.buildBookLightweightDto();
        DtoUtil.addAuthors(bookDto);
        Page<BookLightweightDto> page = new PageImpl<>(List.of(bookDto), PageRequest.of(0, 2), 5);
        doReturn(page).when(service).findByTitle(eq("How to Brew"), any(Pageable.class));

        mockMvc.perform(get("/api/books").param("title", "How to Brew"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value("How to Brew"))
                .andExpect(jsonPath("$.content[0].authors[0].name").value("John Palmer"));
    }

    @Test
    @DisplayName("GET /api/books/{id} should return a book")
    void getBookById() throws Exception {
        BookDto bookDto = DtoUtil.buildBookDto();
        doReturn(bookDto).when(service).findById(TestUtil.BOOK_ID);

        mockMvc.perform(get("/api/books/{id}", TestUtil.BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("How to Brew"));
    }

    @Test
    @DisplayName("POST /api/books should create a book")
    void createBook() throws Exception {
        BookLightweightDto bookDto = DtoUtil.buildBookLightweightDto();
        doReturn(bookDto).when(service).createOrUpdateBook(any(BookDto.class));

        String body = """
                {
                  "id": "%s",
                  "title": "How to Brew",
                  "publicationDate": "2017-06-01",
                  "summary": "From the simplest of recipes to the construction of all grain systems, this 400 page volume covers the widest variety of homebrewing subjects we have seen.",
                  "pageCount": 400,
                  "authors": [{"name" : "John Palmer"}]
                }
                """.formatted(TestUtil.BOOK_ID);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("How to Brew"));
    }

    @Test
    @DisplayName("PUT /api/books should update a book")
    void updateBook() throws Exception {
        BookLightweightDto bookDto = DtoUtil.buildBookLightweightDto();
        doReturn(bookDto).when(service).createOrUpdateBook(any(BookDto.class));

        String body = """
                {
                  "id": "%s",
                  "title": "How to Brew",
                  "publicationDate": "2017-06-01",
                  "summary": "From the simplest of recipes to the construction of all grain systems, this 400 page volume covers the widest variety of homebrewing subjects we have seen.",
                  "pageCount": 400,
                  "authors": [{"name" : "John Palmer"}]
                }
                """.formatted(TestUtil.BOOK_ID);

        mockMvc.perform(put("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("How to Brew"));
    }

    @Test
    @DisplayName("DELETE /api/books/{id} should delete if exists")
    void deleteBookWhenExists() throws Exception {
        doReturn(true).when(service).existsById(TestUtil.BOOK_ID);

        mockMvc.perform(delete("/api/books/{id}", TestUtil.BOOK_ID))
                .andExpect(status().isNoContent());

        Mockito.verify(service).deleteById(TestUtil.BOOK_ID);
    }

    @Test
    @DisplayName("DELETE /api/books/{id} should return 404 if not exists")
    void deleteBookWhenNotExists() throws Exception {
        doReturn(false).when(service).existsById(TestUtil.BOOK_ID);

        mockMvc.perform(delete("/api/books/{id}", TestUtil.BOOK_ID))
                .andExpect(status().isNotFound());
    }
}
