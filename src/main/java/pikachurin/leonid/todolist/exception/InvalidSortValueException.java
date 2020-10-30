package pikachurin.leonid.todolist.exception;

import org.springframework.http.HttpStatus;

/**
 * Исключение: Неверное значение сортировки
 */
public class InvalidSortValueException extends RequestException {

    public InvalidSortValueException(String value) {
        super("Значения сортировки " + value + " не существует", HttpStatus.BAD_REQUEST);
    }
}
