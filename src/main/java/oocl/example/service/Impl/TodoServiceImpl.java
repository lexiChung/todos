package oocl.example.service.Impl;

import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.Objects;
import oocl.example.Exception.IncompleteTodoException;
import oocl.example.Exception.TodoWithEmptyTextException;
import oocl.example.domain.Todo;
import oocl.example.dto.TodoDTO;
import oocl.example.repository.TodoDBRepository;
import oocl.example.service.TodoService;
import org.springframework.beans.BeanUtils;
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
    if (StringUtils.isEmpty(todoDto.getText())) {
      throw new TodoWithEmptyTextException("Todo text cannot be empty");
    }
    Todo todo = new Todo();
    todo.setText(todoDto.getText());
    todo.setDone(false);
    todoDBRepository.save(todo);
  }

  @Override
  public Todo update(int id, Todo todo) {
    if(todo.getDone() == null || todo.getText() == null) {
       throw new IncompleteTodoException("Todo text or done cannot be null");
    }
    Todo updateTodo = todoDBRepository.getTodoById(id);
    if(updateTodo == null) return null;
    updateTodo.setText(todo.getText());
    updateTodo.setDone(todo.getDone());
    todoDBRepository.save(updateTodo);
    return updateTodo;
  }

}
