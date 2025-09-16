package oocl.example.repository;

import java.util.List;
import oocl.example.domain.Todo;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoDBRepository {
  List<Todo> getTodos();

  void save(Todo todo);

  void clear();
}
