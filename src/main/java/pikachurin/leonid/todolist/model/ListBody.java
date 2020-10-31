package pikachurin.leonid.todolist.model;

import lombok.*;
import pikachurin.leonid.todolist.exception.*;

/**
 * Тело запроса для Списка задач
 */
public class ListBody {
    @Getter @Setter
    private String name = null;

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
    }
}
