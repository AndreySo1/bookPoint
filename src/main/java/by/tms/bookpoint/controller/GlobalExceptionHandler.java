package by.tms.bookpoint.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e) {
//        String errorMessage = "Constraint violation: " + e.getMostSpecificCause().getMessage();
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT) // HTTP 409 Conflict
//                .body(errorMessage);
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // HTTP 400 Bad Request
                .body(e.getMessage());
    }
}

