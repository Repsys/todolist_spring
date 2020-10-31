package pikachurin.leonid.todolist.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pikachurin.leonid.todolist.entity.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * JPA Репозиторий Списков задач
 */
public interface ListRepo extends JpaRepository<ListEnt, UUID> {

}
