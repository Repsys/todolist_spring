package pikachurin.leonid.todolist.model;

import lombok.*;

import java.util.UUID;

public class TaskRequestBody {
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private Boolean isDone;

    public TaskRequestBody()
    {
        name = "";
        description = "";
        priority = 0;
        isDone = false;
    }
}
