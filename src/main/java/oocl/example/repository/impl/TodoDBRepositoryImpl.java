package oocl.example.repository.impl;

import java.util.List;
import oocl.example.domain.Todo;
import oocl.example.repository.TodoDBRepository;
import oocl.example.repository.TodoJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TodoDBRepositoryImpl implements TodoDBRepository {

  @Autowired
  private TodoJPARepository todoJPARepository;

  @Override
  public List<Todo> getTodos() {
    return todoJPARepository.findAll();
  }

  @Override
  public void save(Todo todo) {
    todoJPARepository.save(todo);
  }

  @Override
  public void clear() {
    todoJPARepository.deleteAll();
  }

  @Override
  public Todo getTodoById(int id) {
    return todoJPARepository.findById(id).orElse(null);
  }
}
