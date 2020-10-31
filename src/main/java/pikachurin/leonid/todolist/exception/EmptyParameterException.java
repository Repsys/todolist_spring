package pikachurin.leonid.todolist.exception;

import org.springframework.http.HttpStatus;

/**
 * Исключение: Пустое имя
 */
public class EmptyParameterException extends RequestException {

    public EmptyParameterException(String parameterName) {
        super("Параметр '" + parameterName + "' не может быть пустым", HttpStatus.BAD_REQUEST);
    }
}
