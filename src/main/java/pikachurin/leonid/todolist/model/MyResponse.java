package pikachurin.leonid.todolist.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Класс любого ответа
 */
public class MyResponse {
    @Getter
    private final String message;
    @Getter
    private final HttpStatus status;
    @Getter
    private final int code;

    public MyResponse(String message, HttpStatus status)
    {
        this.message = message;
        this.status = status;
        code = status.value();
    }
}
