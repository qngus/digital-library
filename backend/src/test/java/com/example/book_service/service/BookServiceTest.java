package com.example.book_service.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.book_service.dto.BookDto;
import com.example.book_service.dto.BookLightweightDto;
import com.example.book_service.entity.BookEntity;
import com.example.book_service.mapper.BookMapper;
import com.example.book_service.repository.AuthorRepository;
import com.example.book_service.repository.BookRepository;
import com.example.book_service.util.DtoUtil;
import com.example.book_service.util.EntityUtil;
import com.example.book_service.util.TestUtil;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookService service;

    @Test
    void findAll_shouldReturnBooks_whenHappyPath() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<BookEntity> entities = new PageImpl<>(EntityUtil.buildBookEntities());
        EntityUtil.addAuthor(entities.getContent().get(0));
        List<BookLightweightDto> dtos = DtoUtil.buildBookLightweightDtos();
        Page<BookLightweightDto> dtosPage = new PageImpl<>(dtos, pageable, dtos.size());
        DtoUtil.addAuthors(dtosPage.getContent().get(0));
        doReturn(entities).when(bookRepository).findAll(pageable);
        doReturn(dtosPage).when(mapper).toLightweightDtoPage(entities);

        Page<BookLightweightDto> result = service.findAll(pageable);

        assertEquals(dtos, result.getContent());
        verify(bookRepository).findAll(pageable);
        verify(mapper).toLightweightDtoPage(entities);
    }

    @Test
    void findById_shouldReturnASingleBook_whenHappyPath() {
        BookEntity entity = EntityUtil.buildBookEntity();
        EntityUtil.addAuthor(entity);
        BookDto dto = DtoUtil.buildBookDto();
        DtoUtil.addAuthors(dto);

        doReturn(Optional.of(entity)).when(bookRepository).findById(TestUtil.BOOK_ID);
        doReturn(dto).when(mapper).toDto(entity);

        BookDto result = service.findById(TestUtil.BOOK_ID);

        assertEquals(dto, result);
        verify(bookRepository).findById(TestUtil.BOOK_ID);
        verify(mapper).toDto(entity);
    }

    @Test
    void findByAuthorName_shouldReturnBooks_whenHappyPath() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<BookEntity> entities = new PageImpl<>(EntityUtil.buildBookEntities());
        EntityUtil.addAuthor(entities.getContent().get(0));
        List<BookLightweightDto> dtos = DtoUtil.buildBookLightweightDtos();
        Page<BookLightweightDto> dtosPage = new PageImpl<>(dtos, pageable, dtos.size());
        DtoUtil.addAuthors(dtosPage.getContent().get(0));

        doReturn(entities).when(bookRepository).findByAuthors_NameContainingIgnoreCase(TestUtil.AUTHOR_NAME, pageable);
        doReturn(dtosPage).when(mapper).toLightweightDtoPage(entities);

        Page<BookLightweightDto> result = service.findByAuthorName(TestUtil.AUTHOR_NAME, pageable);

        assertEquals(dtos, result.getContent());
        verify(bookRepository).findByAuthors_NameContainingIgnoreCase(TestUtil.AUTHOR_NAME, pageable);
        verify(mapper).toLightweightDtoPage(entities);
    }

    @Test
    void findByTitle_shouldReturnBooks_whenHappyPath() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<BookEntity> entities = new PageImpl<>(EntityUtil.buildBookEntities());
        EntityUtil.addAuthor(entities.getContent().get(0));
        List<BookLightweightDto> dtos = DtoUtil.buildBookLightweightDtos();
        Page<BookLightweightDto> dtosPage = new PageImpl<>(dtos, pageable, dtos.size());
        DtoUtil.addAuthors(dtosPage.getContent().get(0));

        doReturn(entities).when(bookRepository).findByTitleContainingIgnoreCase(TestUtil.TITLE, pageable);
        doReturn(dtosPage).when(mapper).toLightweightDtoPage(entities);

        Page<BookLightweightDto> result = service.findByTitle(TestUtil.TITLE, pageable);

        assertEquals(dtos, result.getContent());
        verify(bookRepository).findByTitleContainingIgnoreCase(TestUtil.TITLE, pageable);
        verify(mapper).toLightweightDtoPage(entities);
    }

    @Test
    void createOrUpdateBook_shouldReturnTheSavedBook_whenAuthorAlreadyExists() {
        BookEntity entityWithoutId = EntityUtil.buildBookEntityWithoutId();
        EntityUtil.addAuthor(entityWithoutId);
        BookEntity savedEntity = EntityUtil.buildBookEntity();
        EntityUtil.addAuthor(savedEntity);
        BookDto dto = DtoUtil.buildBookDtoWithoutId();
        DtoUtil.addAuthors(dto);
        BookLightweightDto savedDto = DtoUtil.buildBookLightweightDto();
        DtoUtil.addAuthors(savedDto);


        doReturn(entityWithoutId).when(mapper).toEntity(dto);
        doReturn(Optional.of(EntityUtil.buildAuthorEntity())).when(authorRepository).findByName(dto.getAuthors().get(0).getName());
        doReturn(savedEntity).when(bookRepository).save(entityWithoutId);
        doReturn(savedDto).when(mapper).toBookLightweightDto(savedEntity);

        BookLightweightDto result = service.createOrUpdateBook(dto);

        assertEquals(savedDto, result);
        verify(mapper).toEntity(dto);
        verify(authorRepository).findByName(TestUtil.AUTHOR_NAME);
        verify(bookRepository).save(entityWithoutId);
        verify(mapper).toBookLightweightDto(savedEntity);
    }

    @Test
    void deleteById_shouldCallbookRepositoryToDelte_whenHappyPath() {

        service.deleteById(TestUtil.BOOK_ID);

        verify(bookRepository).deleteById(TestUtil.BOOK_ID);
    }

    @Test
    void existsById_shouldReturnTrue_whenBookExists() {

        doReturn(Boolean.TRUE).when(bookRepository).existsById(TestUtil.BOOK_ID);

        Boolean result = service.existsById(TestUtil.BOOK_ID);

        assertTrue(result);
        verify(bookRepository).existsById(TestUtil.BOOK_ID);
    }

    @Test
    void existsById_shouldReturnFalse_whenBookDoesNotExists() {

        doReturn(Boolean.FALSE).when(bookRepository).existsById(TestUtil.BOOK_ID);

        Boolean result = service.existsById(TestUtil.BOOK_ID);

        assertFalse(result);
        verify(bookRepository).existsById(TestUtil.BOOK_ID);
    }
    
}
