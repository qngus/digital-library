package com.example.book_service.util;

import java.util.List;
import java.util.Set;

import com.example.book_service.entity.AuthorEntity;
import com.example.book_service.entity.BookEntity;

public class EntityUtil {
    
    public static BookEntity buildBookEntity() {
        return BookEntity.builder().id(TestUtil.BOOK_ID).pageCount(TestUtil.PAGE_COUNT).publicationDate(TestUtil.PUBLICATION_DATE).summary(TestUtil.SUMMARY).title(TestUtil.TITLE).build();
    }

    public static BookEntity buildBookEntityWithoutId() {
        BookEntity entity = buildBookEntity();
        entity.setId(null);
        return entity;
    }

    public static List<BookEntity> buildBookEntities() {
        return List.of(buildBookEntity());
    }

    public static AuthorEntity buildAuthorEntity() {
        return AuthorEntity.builder().id(TestUtil.AUTHOR_ID).name(TestUtil.AUTHOR_NAME).build();
    }

    public static List<AuthorEntity> buildAuthorEntities() {
        return List.of(buildAuthorEntity());
    }

    public static void addAuthor(BookEntity book) {
        book.setAuthors(Set.of(buildAuthorEntity()));
        book.getAuthors().iterator().next().setBooks(Set.of(book));
    }

    public static void addBook(AuthorEntity author) {
        author.setBooks(Set.of(buildBookEntity()));
        author.getBooks().iterator().next().setAuthors(Set.of(author));
    }

}
