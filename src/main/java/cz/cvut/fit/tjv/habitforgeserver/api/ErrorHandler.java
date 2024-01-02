package cz.cvut.fit.tjv.habitforgeserver.api;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cz.cvut.fit.tjv.habitforgeserver.service.EntityAlreadyExistsException;
import cz.cvut.fit.tjv.habitforgeserver.service.EntityNotFoundException;
import cz.cvut.fit.tjv.habitforgeserver.service.ReferencedEntityDoesNotExistException;
import io.swagger.v3.oas.annotations.Hidden;

@RestControllerAdvice
@Hidden
public class ErrorHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        LocalDateTime.now();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleNotFoundException(EntityNotFoundException e) {
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public void handleEntityALreadyExistsException(EntityAlreadyExistsException e) {
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ReferencedEntityDoesNotExistException.class)
    public void handleReferencedEntityDoesNotExistException(ReferencedEntityDoesNotExistException e) {
    }
}
