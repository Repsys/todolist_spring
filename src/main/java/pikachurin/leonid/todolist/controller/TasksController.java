package pikachurin.leonid.todolist.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import pikachurin.leonid.todolist.entity.*;
import pikachurin.leonid.todolist.model.*;
import pikachurin.leonid.todolist.service.TasksService;

import java.util.*;

/**
 * Контроллер запросов по операциям с Задачами
 */
@Api(description = "Операции с задачами")
@RestController
public class TasksController {
    @Autowired
    private TasksService tasksService;

    /**
     * Получить задачи из списка с пагинацией
     * @param listId - id списка
     * @param quantity - количество задач на странице
     * @param page - номер страницы
     * @return массив задач
     */
    @ApiOperation(value = "Получить задачи из списка с пагинацией",
            response = TaskEnt.class,
            responseContainer = "List")
    @GetMapping("/lists/{list_id}/tasks")
    public ResponseEntity<List<TaskEnt>> getTasks(
            @PathVariable("list_id") UUID listId,
            @RequestParam(required = false, defaultValue = "10") Integer quantity,
            @RequestParam(required = false, defaultValue = "0") Integer page)
    {
        List<TaskEnt> tasks = tasksService.getTasks(listId, quantity, page);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Получить задачу по её id
     * @param id - id задачи
     * @return задача
     */
    @ApiOperation(value = "Получить задачу по её id",
            response = TaskEnt.class)
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskEnt> getTask(
            @PathVariable("id") UUID id)
    {
        TaskEnt task = tasksService.getTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Создать новую задачу в списке
     * @param listId - id списка
     * @param taskBody - тело запроса
     * @return созданная задача
     */
    @ApiOperation(value = "Создать новую задачу в списке",
            response = TaskEnt.class)
    @PostMapping("/lists/{list_id}/tasks")
    public ResponseEntity<TaskEnt> addTask(
            @PathVariable("list_id") UUID listId,
            @RequestBody TaskBody taskBody)
            throws MissingServletRequestParameterException
    {
        TaskEnt task = tasksService.addTask(listId, taskBody);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    /**
     * Изменить задачу
     * @param id - id задачи
     * @param taskBody - тело запроса
     * @return изменённая задача
     */
    @ApiOperation(value = "Изменить задачу",
            response = TaskEnt.class)
    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskEnt> modifyTask(
            @PathVariable("id") UUID id,
            @RequestBody TaskBody taskBody)
    {
        TaskEnt task = tasksService.modifyTask(id, taskBody);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Пометить задачу как сделанную
     * @param id - id задачи
     */
    @ApiOperation(value = "Пометить задачу как сделанную",
            response = MyResponse.class)
    @PutMapping("/tasks/mark-done/{id}")
    public ResponseEntity<MyResponse> markAsDoneTask(
            @PathVariable("id") UUID id)
    {
        tasksService.markAsDoneTask(id);
        return new ResponseEntity<>(new MyResponse("Задача успешно выполнена", HttpStatus.OK), HttpStatus.OK);
    }

    /**
     * Удалить задачу из списка
     * @param id - id задачи
     */
    @ApiOperation(value = "Удалить задачу из списка",
            response = MyResponse.class)
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<MyResponse> removeTask(
            @PathVariable("id") UUID id)
    {
        tasksService.removeTask(id);
        return new ResponseEntity<>(new MyResponse("Задача успешно удалена", HttpStatus.OK), HttpStatus.OK);
    }

}
