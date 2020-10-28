package pikachurin.leonid.todolist.model;

import lombok.*;

public class ListRequestBody {
    @Getter @Setter
    private String name;

    public ListRequestBody()
    {
        name = "";
    }
}
