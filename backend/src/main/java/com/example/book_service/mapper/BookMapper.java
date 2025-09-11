package com.example.book_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.example.book_service.dto.BookDto;
import com.example.book_service.dto.BookLightweightDto;
import com.example.book_service.entity.BookEntity;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {

    BookEntity toEntity(BookDto dto);

    BookDto toDto(BookEntity entity);

    BookLightweightDto toBookLightweightDto(BookEntity entity);

    List<BookEntity> toEntities(List<BookDto> dtos);

    List<BookDto> toDtos(List<BookEntity> entities);

    List<BookLightweightDto> toBookLightweightDtos(List<BookEntity> entities);

    default Page<BookLightweightDto> toLightweightDtoPage(Page<BookEntity> books) {
        return books.map(this::toBookLightweightDto);
    }

}
