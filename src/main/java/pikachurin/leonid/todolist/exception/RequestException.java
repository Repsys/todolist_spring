package pikachurin.leonid.todolist.exception;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class RequestException extends RuntimeException {
    @Getter
    private HttpStatus status;

    public RequestException(String message, HttpStatus status)
    {
        super(message);
        this.status = status;
    }
}
