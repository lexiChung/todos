package oocl.example.service.Impl;

import java.util.List;
import oocl.example.domain.Todo;
import oocl.example.dto.TodoDTO;
import oocl.example.repository.TodoDBRepository;
import oocl.example.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {
  @Autowired
  private TodoDBRepository todoDBRepository;

  @Override
  public List<Todo> getTodos() {
    return todoDBRepository.getTodos();
  }

  @Override
  public void createTodo(TodoDTO todoDto) {
    Todo todo = new Todo();
    todo.setText(todoDto.getText());
    todoDBRepository.save(todo);
  }

}
