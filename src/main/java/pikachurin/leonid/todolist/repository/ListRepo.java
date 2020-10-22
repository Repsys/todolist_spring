package pikachurin.leonid.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pikachurin.leonid.todolist.entity.ListEnt;

import java.util.UUID;

public interface ListRepo extends JpaRepository<ListEnt, UUID> {

}
