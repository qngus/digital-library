package com.example.book_service.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book_service.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {

    public Page<BookEntity> findByAuthors_NameContainingIgnoreCase(String authorName, Pageable pageable);

    public Page<BookEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    public Page<BookEntity> findAllByAuthorsNameAndTitleIgnoreCase(String authorName, String title, Pageable pageable);
}
