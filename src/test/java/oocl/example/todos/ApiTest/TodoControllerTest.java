package oocl.example.todos.ApiTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import oocl.example.repository.TodoDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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
      .andExpect(jsonPath("$.message").value("Create todo successfully"));
  }

  @Test
  void should_return_422_when_create_todo_with_empty_text_todo() throws Exception {
    String request = """
          {
              "text": ""
          }
      """;

    mockMvc.perform(MockMvcRequestBuilders.post("/todo")
      .contentType(MediaType.APPLICATION_JSON)
      .content(request))
      .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_return_200_when_update_todo_with_valid_todo() throws Exception {
    String createRequest = """
          {
              "text": "text3"
          }
      """;
    mockMvc.perform(MockMvcRequestBuilders.post("/todo")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createRequest))
        .andExpect(status().isOk());
    String response = mockMvc.perform(MockMvcRequestBuilders.get("/todo/list")
        .contentType(MediaType.APPLICATION_JSON))
      .andReturn()
      .getResponse()
      .getContentAsString();
    Integer id = JsonPath.parse(response).read("$.result[0].id", Integer.class);

    String updateRequest = """
          {
              "id": %d,
              "text": "text4",
              "done": true
          }
      """.formatted(id);
    mockMvc.perform(MockMvcRequestBuilders.put("/todo/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequest))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.result.text").value("text4"))
      .andExpect(jsonPath("$.result.done").value(true));
  }

  @Test
  void should_return_404_when_update_todo_with_invalid_id() throws Exception {
    int id = 1;
    String updateRequest = """
          {
              "id": %d,
              "text": "text4",
              "done": true
          }
      """.formatted(id);
    mockMvc.perform(MockMvcRequestBuilders.put("/todo/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequest))
      .andExpect(jsonPath("$.code").value("404"))
      .andExpect(jsonPath("$.message").value("Todo not found"));
  }

  @Test
  void should_return_422_when_update_todo_with_incomplete_todo() throws Exception {
    String createRequest = """
          {
              "text": "text3"
          }
      """;
    mockMvc.perform(MockMvcRequestBuilders.post("/todo")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createRequest))
        .andExpect(status().isOk());
    String response = mockMvc.perform(MockMvcRequestBuilders.get("/todo/list")
        .contentType(MediaType.APPLICATION_JSON))
      .andReturn()
      .getResponse()
      .getContentAsString();
    Integer id = JsonPath.parse(response).read("$.result[0].id", Integer.class);

    String updateRequest = """
          {
              "id": %d,
              "done": true
          }
      """.formatted(id);
    mockMvc.perform(MockMvcRequestBuilders.put("/todo/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequest))
      .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_return_204_when_delete_todo_with_valid_id() throws Exception {
    String createRequest = """
          {
              "text": "text3"
          }
      """;
    mockMvc.perform(MockMvcRequestBuilders.post("/todo")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createRequest))
        .andExpect(status().isOk());
    String response = mockMvc.perform(MockMvcRequestBuilders.get("/todo/list")
        .contentType(MediaType.APPLICATION_JSON))
      .andReturn()
      .getResponse()
      .getContentAsString();
    Integer id = JsonPath.parse(response).read("$.result[0].id", Integer.class);

    mockMvc.perform(MockMvcRequestBuilders.delete("/todo/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.code").value("204"));
  }

  @Test
  void should_return_404_when_delete_todo_with_invalid_id() throws Exception {
    int id = 1;
    mockMvc.perform(MockMvcRequestBuilders.delete("/todo/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.code").value("404"))
      .andExpect(jsonPath("$.message").value("Todo not found"));
  }
}
