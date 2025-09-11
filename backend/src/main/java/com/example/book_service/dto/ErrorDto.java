package com.example.book_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDto {
    
    private int httpStatusValue;

    private String errorCause;

    private String message;
    
}
