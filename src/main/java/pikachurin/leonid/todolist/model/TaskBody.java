package pikachurin.leonid.todolist.model;

import lombok.*;
import pikachurin.leonid.todolist.exception.*;

/**
 * Тело запроса для Задачи
 */
public class TaskBody {
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
            else if (name.length() > 128)
                throw new TooLongStringParameterException("name", 128);
        }
        if (description != null) {
            if (description.length() > 256)
                throw new TooLongStringParameterException("description", 256);
        }
        if (priority != null) {
            if (priority < 0) priority = 0;
            else if (priority > 5) priority = 5;
        }
    }
}
