package oocl.example.Exception;

public class IncompleteTodoException extends RuntimeException{

  public IncompleteTodoException(String message) {
    super(message);
  }
}
