package oocl.example.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import oocl.example.common.CommonResult;
import oocl.example.domain.Todo;
import oocl.example.dto.TodoDTO;
import oocl.example.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/todo")
public class TodoController {
  @Autowired
  private TodoService todoService;

  @GetMapping("/list")
  public CommonResult<List<Todo>> getTodos() {
    List<Todo> todos = todoService.getTodos();
    log.info(todos.toString());
    return CommonResult.ok(todos);
  }

  @PostMapping()
  public CommonResult<String> createTodo(@RequestBody TodoDTO todoDto) {
    todoService.createTodo(todoDto);
    return CommonResult.ok("Create todo successfully");
  }

  @PutMapping("/{id}")
  public CommonResult<Todo> update(@PathVariable int id, @RequestBody Todo todo) {
    Todo updatedTodo = todoService.update(id, todo);
    if(updatedTodo == null) {
      return CommonResult.notFound("Todo not found");
    }
    return CommonResult.ok(updatedTodo);
  }

  @DeleteMapping("/{id}")
  public CommonResult<String> deleteById(@PathVariable int id) {
    todoService.deleteById(id);
    return CommonResult.deleteSuccess();
  }
}
