package pikachurin.leonid.todolist.controller;

import org.springframework.http.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import pikachurin.leonid.todolist.exception.*;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(RequestException.class)
    public ResponseEntity handleException(RequestException exception)
    {
        MyResponse response = new MyResponse(exception.getMessage(), exception.getStatus());
        return new ResponseEntity(response, response.getStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingParams(MissingServletRequestParameterException exception)
    {
        String name = exception.getParameterName();
        MyResponse response = new MyResponse("Параметр " + name + " не задан", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, response.getStatus());
    }
}
