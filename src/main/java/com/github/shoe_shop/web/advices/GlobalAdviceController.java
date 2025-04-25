package com.github.shoe_shop.web.advices;

import com.github.shoe_shop.exceptions.BadArgumentsException;
import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import javax.naming.AuthenticationException;

@RestControllerAdvice
@Slf4j
public class GlobalAdviceController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(final Exception e) {
        if (e instanceof EntityAlreadyExistsException entityAlreadyExistsException) {
            return ResponseEntity.badRequest().body("Entity you're trying to create already exists." + e.getMessage());
        } else if (e instanceof EntityNotFoundException entityNotFoundException) {
            return new ResponseEntity<>("Entity you're looking for is not found." + e.getMessage(), HttpStatus.NOT_FOUND);
        } else if (e instanceof BadArgumentsException badArgumentsException) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } else if (e instanceof AccessDeniedException accessDeniedException) {
            return new ResponseEntity<>(accessDeniedException.getMessage(), HttpStatus.FORBIDDEN);
        } else if (e instanceof AuthenticationException authenticationException) {
            return new ResponseEntity<>(authenticationException.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if (e instanceof HttpMessageNotReadableException httpMessageNotReadableException) {
            return new ResponseEntity<>(httpMessageNotReadableException.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (e instanceof HandlerMethodValidationException handlerMethodValidationException) {
            log.error("",handlerMethodValidationException);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        else {
            return ResponseEntity.internalServerError().body("Unknown exception with message: " + e.getMessage());
        }
    }
}
