package pikachurin.leonid.todolist.model;

import lombok.*;
import pikachurin.leonid.todolist.exception.*;

/**
 * Тело запроса для Задачи
 */
public class TaskBody {
    private static final int MAX_NAME_LENGTH = 128;
    private static final int MAX_DESC_LENGTH = 256;
    private static final int MIN_PRIORITY = 0;
    private static final int MAX_PRIORITY = 5;

    @Getter @Setter
    private String name = null;

    @Getter @Setter
    private String description = null;

    @Getter @Setter
    private Integer priority = null;

    @Getter @Setter
    private Boolean isDone = null;

    /**
     * Проверить все ненулевые поля на правильность и по возможности исправить или выкинуть исключение
     */
    public void validate() {
        if (name != null) {
            if (name.isBlank())
                throw new EmptyParameterException("name");
            else if (name.length() > MAX_NAME_LENGTH)
                throw new TooLongStringParameterException("name", MAX_NAME_LENGTH);
        }
        if (description != null) {
            if (description.length() > MAX_DESC_LENGTH)
                throw new TooLongStringParameterException("description", MAX_DESC_LENGTH);
        }
        if (priority != null) {
            if (priority < MIN_PRIORITY) priority = MIN_PRIORITY;
            else if (priority > MAX_PRIORITY) priority = MAX_PRIORITY;
        }
    }
}
