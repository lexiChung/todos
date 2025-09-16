package oocl.example.common;

import oocl.example.Exception.TodoWithEmptyTextException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(TodoWithEmptyTextException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public String handleTodoWithEmptyTextException(Exception e) {
    return e.getMessage();
  }
}
