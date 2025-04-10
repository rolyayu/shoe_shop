package com.github.shoe_shop.web.advices;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
@Slf4j
public class GlobalAdviceController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(final Exception e) {
        if (e instanceof EntityAlreadyExistsException entityAlreadyExistsException) {
            return ResponseEntity.badRequest().body("entityAlreadyExistsException handled with message: "
                    + entityAlreadyExistsException.getMessage());
        } else if(e instanceof AccessDeniedException accessDeniedException) {
            return new ResponseEntity<>(accessDeniedException.getMessage(), HttpStatus.FORBIDDEN);
        } else if (e instanceof AuthenticationException authenticationException) {
            return new ResponseEntity<>(authenticationException.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if(e instanceof HttpMessageNotReadableException httpMessageNotReadableException) {
            return new ResponseEntity<>(httpMessageNotReadableException.getMessage(), HttpStatus.BAD_REQUEST);
        }
        else {
            return ResponseEntity.internalServerError().body("Unknown exception with message: " + e.getMessage());
        }
    }
}
