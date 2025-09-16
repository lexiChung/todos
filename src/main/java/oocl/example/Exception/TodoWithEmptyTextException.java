package oocl.example.Exception;

public class TodoWithEmptyTextException extends RuntimeException{

  public TodoWithEmptyTextException(String message) {
    super(message);
  }
}
