package pingwit.beautysaloon.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pingwit.beautysaloon.exception.BeautySalonNotFoundException;
import pingwit.beautysaloon.exception.BeautySalonValidationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BeautySalonNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(BeautySalonNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(BeautySalonValidationException.class)
    public ResponseEntity<String> handleValidationException(BeautySalonValidationException e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.toString());
    }
}
