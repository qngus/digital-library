package com.example.book_service.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private UUID id;

    @NotBlank
    @Size(max = 255, message = "The book title should contain less than 255 characters")
    private String title;

    @NotEmpty
    private List<AuthorDto> authors;

    @NotNull
    private LocalDate publicationDate;

    @Size(max = 2000, message = "The book summary should contain less than 2000 characters")
    @NotBlank
    private String summary;

    @NotNull
    @Min(value = 1, message = "The book should have at least 1 page")
    private Integer pageCount;

}