package io.hskim.learnapi.config;

import io.hskim.learnapi.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerConfig {

  @ExceptionHandler(value = { UserNotFoundException.class })
  public ResponseEntity<?> handleUserNotFoundException(Exception e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(value = { MethodArgumentNotValidException.class })
  public ResponseEntity<?> handleMethodArgumentNotValidException(
    MethodArgumentNotValidException e
  ) {
    return ResponseEntity
      .badRequest()
      .body(
        e.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList()
      );
  }
}
