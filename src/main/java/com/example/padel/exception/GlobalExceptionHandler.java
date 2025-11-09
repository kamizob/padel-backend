package com.example.padel.exception;

import com.example.padel.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- VALIDATION ERRORS (from @Valid annotations) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // --- 400 BAD REQUEST group (user input errors) ---
    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            UserNotFoundException.class,
            UserNotVerifiedException.class,
            InvalidPasswordException.class,
            CourtNotFoundException.class,
            InvalidCourtConfigurationException.class
    })
    public ResponseEntity<Map<String, String>> handleBadRequest(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    // --- 500 SERVER ERROR group (unexpected/internal issues) ---
    @ExceptionHandler({
            SignUpFailedException.class,
            FailedCreateCourtException.class,
            FailedUpdateCourtException.class,
            FailedCreateBookingException.class
    })
    public ResponseEntity<Map<String, String>> handleServerError(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
    }

    // --- Fallback catch-all (if something slipped through) ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Unexpected error: " + ex.getMessage()));
    }
}
