package com.example.book_service.util;

import java.util.List;

import com.example.book_service.dto.AuthorDto;
import com.example.book_service.dto.BookDto;
import com.example.book_service.dto.BookLightweightDto;

public class DtoUtil {

    public static List<BookDto> buildBookDtos() {
        return List.of(buildBookDto());
    }

    public static BookDto buildBookDto() {
        return BookDto.builder().id(TestUtil.BOOK_ID).title(TestUtil.TITLE).pageCount(TestUtil.PAGE_COUNT).publicationDate(TestUtil.PUBLICATION_DATE).summary(TestUtil.SUMMARY).build();
    }

    public static List<BookLightweightDto> buildBookLightweightDtos() {
        return List.of(buildBookLightweightDto());
    }

    public static BookLightweightDto buildBookLightweightDto() {
        return BookLightweightDto.builder().id(TestUtil.BOOK_ID).title(TestUtil.TITLE).build();
    }

    public static BookDto buildBookDtoWithoutId() {
        BookDto dto = buildBookDto();
        dto.setId(null);
        return dto;
    }

    public static AuthorDto buildAuthorDto() {
        return AuthorDto.builder().id(TestUtil.AUTHOR_ID).name(TestUtil.AUTHOR_NAME).build();
    }

    public static List<AuthorDto> buildAuthorDtos() {
        return List.of(buildAuthorDto());
    }

    public static void addAuthors(BookDto book) {
        book.setAuthors(buildAuthorDtos());
    }

    public static void addAuthors(BookLightweightDto book) {
        book.setAuthors(buildAuthorDtos());
    }

}
