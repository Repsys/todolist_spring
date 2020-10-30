package pikachurin.leonid.todolist.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Исключение: Список не найден
 */
public class ListNotFoundException extends RequestException {

    public ListNotFoundException(UUID id) {
        super("Cписок c id = " + id + " не найден", HttpStatus.NOT_FOUND);
    }
}
