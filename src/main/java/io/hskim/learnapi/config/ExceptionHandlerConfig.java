package io.hskim.learnapi.config;

import io.hskim.learnapi.user.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerConfig {

  @ExceptionHandler(value = { UserNotFoundException.class })
  public ResponseEntity<?> handleUserNotFoundException(Exception e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(value = { MethodArgumentNotValidException.class })
  @ResponseStatus(code = HttpStatus.BAD_REQUEST) //Swagger-ui에서 공통적으로 나타남
  @Hidden
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
