package pikachurin.leonid.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pikachurin.leonid.todolist.entity.TaskEnt;

import java.util.UUID;

public interface TaskRepo extends JpaRepository<TaskEnt, UUID> {

}
