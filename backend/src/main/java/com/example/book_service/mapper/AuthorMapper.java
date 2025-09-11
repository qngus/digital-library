package com.example.book_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.book_service.dto.AuthorDto;
import com.example.book_service.entity.AuthorEntity;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface AuthorMapper {

    @Mapping(target = "books", ignore = true) 
    AuthorEntity toEntity(AuthorDto dto);

    AuthorDto toDto(AuthorEntity entity);

    List<AuthorEntity> toEntities(List<AuthorDto> dtos);

    List<AuthorDto> toDtos(List<AuthorEntity> entities);

}
