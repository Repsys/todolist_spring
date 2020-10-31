package pikachurin.leonid.todolist.controller;

import org.springframework.http.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import pikachurin.leonid.todolist.exception.*;
import pikachurin.leonid.todolist.model.MyResponse;

/**
 * Контроллер для обработки исключений
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * Обработать исключение запроса
     * @param exception - исключение
     * @return ответ с исключением
     */
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<MyResponse> handleException(RequestException exception)
    {
        MyResponse response = new MyResponse(exception.getMessage(), exception.getStatus());
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Обработать исключение отсутствия требуемых параметров
     * @param exception - исключение
     * @return ответ с исключением
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<MyResponse> handleMissingParams(MissingServletRequestParameterException exception)
    {
        String name = exception.getParameterName();
        MyResponse response = new MyResponse("Параметр '" + name + "' не задан", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
