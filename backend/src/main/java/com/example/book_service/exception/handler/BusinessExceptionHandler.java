package com.example.book_service.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.book_service.dto.ErrorDto;
import com.example.book_service.exception.BookNotFoundException;

import io.swagger.v3.oas.annotations.Hidden;

@RestControllerAdvice
@Hidden
public class BusinessExceptionHandler {
    
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFound(BookNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder().httpStatusValue(HttpStatus.NOT_FOUND.value()).errorCause(HttpStatus.NOT_FOUND.getReasonPhrase()).message(ex.getMessage()).build());
    }

}
