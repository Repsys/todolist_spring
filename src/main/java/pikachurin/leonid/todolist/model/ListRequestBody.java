package pikachurin.leonid.todolist.model;

import lombok.*;

/**
 * Тело запроса для Списка задач
 */
public class ListRequestBody {
    @Getter @Setter
    private String name;

    public ListRequestBody()
    {
        name = "";
    }
}
