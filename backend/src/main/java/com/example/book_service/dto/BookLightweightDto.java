package com.example.book_service.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookLightweightDto {

    private UUID id;

    private String title;

    private List<AuthorDto> authors;

}