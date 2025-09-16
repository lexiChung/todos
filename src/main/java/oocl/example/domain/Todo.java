package oocl.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String text;
  private Boolean done;
}
