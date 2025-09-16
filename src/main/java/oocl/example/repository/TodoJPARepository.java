package oocl.example.repository;

import oocl.example.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoJPARepository extends JpaRepository<Todo,Integer> {

}
