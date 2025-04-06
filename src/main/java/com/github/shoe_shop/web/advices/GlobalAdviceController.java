package com.github.shoe_shop.web.advices;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalAdviceController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(final Exception e) {
        if (e instanceof EntityAlreadyExistsException entityAlreadyExistsException) {
            return ResponseEntity.badRequest().body("entityAlreadyExistsException handled with message: "
                    + entityAlreadyExistsException.getMessage());
        } else {
            log.error("Error happen: ",e);
            return ResponseEntity.internalServerError().body("Unknown exception with message: " + e.getMessage());
        }
    }
}
