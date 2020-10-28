package pikachurin.leonid.todolist.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class MyResponse {
    @Getter
    private String message;
    @Getter
    private HttpStatus status;
    @Getter
    private int code;

    public MyResponse(String message, HttpStatus status)
    {
        this.message = message;
        this.status = status;
        code = status.value();
    }
}
