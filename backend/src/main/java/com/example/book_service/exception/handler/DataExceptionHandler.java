package com.example.book_service.exception.handler;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.book_service.dto.ErrorDto;

import io.swagger.v3.oas.annotations.Hidden;

@RestControllerAdvice
@Hidden
public class DataExceptionHandler {
    
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResult(EmptyResultDataAccessException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder().httpStatusValue(HttpStatus.NOT_FOUND.value()).errorCause(HttpStatus.NOT_FOUND.getReasonPhrase()).message("Entity not found"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorDto.builder().httpStatusValue(HttpStatus.CONFLICT.value()).errorCause(HttpStatus.CONFLICT.getReasonPhrase()).message("Data integrity violation"));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccess(DataAccessException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder().httpStatusValue(HttpStatus.INTERNAL_SERVER_ERROR.value()).errorCause(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message("Database error"));
    }

}
