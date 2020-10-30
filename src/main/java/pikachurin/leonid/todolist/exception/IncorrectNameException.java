package pikachurin.leonid.todolist.exception;

import org.springframework.http.HttpStatus;

/**
 * Исключение: Некорректное имя
 */
public class IncorrectNameException extends RequestException {

    public IncorrectNameException() {
        super("Имя задано некорректно", HttpStatus.BAD_REQUEST);
    }
}
