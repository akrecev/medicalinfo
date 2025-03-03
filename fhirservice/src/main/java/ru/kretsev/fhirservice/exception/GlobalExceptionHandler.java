package ru.kretsev.fhirservice.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kretsev.fhirservice.service.LoggingService;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final LoggingService loggingService;

    @ExceptionHandler(PatientJsonProcessingException.class)
    public ResponseEntity<Map<String, String>> handlePatientJsonProcessingException(PatientJsonProcessingException e) {
        loggingService.logError("Patient json processing error: {}", e);

        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid patient JSON format");
        response.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
