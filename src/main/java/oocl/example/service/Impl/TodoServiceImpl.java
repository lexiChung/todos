package oocl.example.service.Impl;

import io.micrometer.common.util.StringUtils;
import java.util.List;
import oocl.example.Exception.TodoWithEmptyTextException;
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
    if(StringUtils.isEmpty(todoDto.getText())){
      throw new TodoWithEmptyTextException("Todo text cannot be empty");
    }
    Todo todo = new Todo();
    todo.setText(todoDto.getText());
    todoDBRepository.save(todo);
  }

}
