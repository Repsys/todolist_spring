package pikachurin.leonid.todolist.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Исключение: Задача не найдена
 */
public class TaskNotFoundException extends RequestException {

    public TaskNotFoundException(UUID id) {
        super("Задача c id = " + id + " не найдена", HttpStatus.NOT_FOUND);
    }
}
