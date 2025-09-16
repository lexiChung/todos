package oocl.example.todos.ApiTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import oocl.example.repository.TodoDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TodoDBRepository todoDBRepository;

  @BeforeEach
  void setUp() {
    todoDBRepository.clear();
  }

  @Test
  void should_return_empty_list_when_get_todos_with_no_todos() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/todo/list")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.result.length()").value(0));
  }

  @Test
  void should_return_todos_list_when_get_todos_with_valid_todos() throws Exception {
    String request = """
          {
              "text": "text3"
          }
      """;

    mockMvc.perform(MockMvcRequestBuilders.post("/todo")
      .contentType(MediaType.APPLICATION_JSON)
      .content(request));

    mockMvc.perform(MockMvcRequestBuilders.get("/todo/list")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.result.length()").value(1));
  }

  @Test
  void should_create_todo_when_create_todo_with_valid_todo() throws Exception {
    String request = """
          {
              "text": "text3"
          }
      """;

    mockMvc.perform(MockMvcRequestBuilders.post("/todo")
      .contentType(MediaType.APPLICATION_JSON)
      .content(request))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.result").value("Create todo successfully"));
  }
}
