package com.example.book_service.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDto {
    
    private UUID id;

    @NotBlank
    @Size(max = 255, message = "The author name should contain less than 255 characters")
    private String name;
    
}
