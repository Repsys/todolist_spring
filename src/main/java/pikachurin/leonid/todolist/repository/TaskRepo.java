package pikachurin.leonid.todolist.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import pikachurin.leonid.todolist.entity.TaskEnt;

import java.util.UUID;

/**
 * JPA Репозиторий Задач
 */
public interface TaskRepo extends JpaRepository<TaskEnt, UUID> {

    /**
     * Получить задачи, относящиеся к определенному списку
     * @param listId - id списка
     * @param pageable - параметры пагинации и сортировки
     * @return страница с задачами
     */
    Page<TaskEnt> findAllByList_Id(UUID listId, Pageable pageable);
}
