package com.jamerson.dsclients.resources.exceptions;

import com.jamerson.dsclients.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = mountStandardError(status, e, request);
        return ResponseEntity.status(status).body(err);
    }

    private StandardError mountStandardError(HttpStatus status, RuntimeException e, HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(status.getReasonPhrase());
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return err;
    }
}
