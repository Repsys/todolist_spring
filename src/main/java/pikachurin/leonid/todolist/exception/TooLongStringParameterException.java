package pikachurin.leonid.todolist.exception;

import org.springframework.http.HttpStatus;

/**
 * Исключение: Пустое имя
 */
public class TooLongStringParameterException extends RequestException {

    public TooLongStringParameterException(String parameterName, int maxSize) {
        super("Строка '" + parameterName + "' должна содержать не больше " + maxSize + " символов" , HttpStatus.BAD_REQUEST);
    }
}
