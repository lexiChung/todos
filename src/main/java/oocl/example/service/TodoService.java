package oocl.example.service;

import java.util.List;
import oocl.example.domain.Todo;
import oocl.example.dto.TodoDTO;

public interface TodoService {
  List<Todo> getTodos();

  void createTodo(TodoDTO todoDto);

  Todo update(int id, Todo todo);

  void deleteById(int id);
}
