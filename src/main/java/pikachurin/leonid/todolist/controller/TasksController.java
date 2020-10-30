package pikachurin.leonid.todolist.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pikachurin.leonid.todolist.entity.*;
import pikachurin.leonid.todolist.exception.*;
import pikachurin.leonid.todolist.model.*;
import pikachurin.leonid.todolist.repository.*;
import pikachurin.leonid.todolist.service.TasksService;

import java.util.*;

@Api(description = "Операции с задачами")
@RestController
public class TasksController {
    @Autowired
    TasksService tasksService;

    @ApiOperation("Получить задачи из списка")
    @GetMapping("lists/{list_id}/tasks")
    public ResponseEntity getTasks(
            @PathVariable("list_id") UUID listId,
            @RequestParam(required = false, defaultValue = "10") Integer quantity,
            @RequestParam(required = false, defaultValue = "0") Integer page)
    {
        List<TaskEnt> tasks = tasksService.getTasks(listId, quantity, page);
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @ApiOperation("Получить задачу по её id")
    @GetMapping("tasks/{id}")
    public ResponseEntity getTask(
            @PathVariable("id") UUID id)
    {
        TaskEnt task = tasksService.getTask(id);
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @ApiOperation("Создать новую задачу в списке")
    @PostMapping("lists/{list_id}/tasks")
    public ResponseEntity addTask(
            @PathVariable("list_id") UUID listId,
            @RequestBody TaskRequestBody taskBody)
    {
        TaskEnt task = tasksService.addTask(listId, taskBody);
        return new ResponseEntity(task, HttpStatus.CREATED);
    }

    @ApiOperation("Изменить задачу")
    @PutMapping("tasks/{id}")
    public ResponseEntity modifyTask(
            @PathVariable("id") UUID id,
            @RequestBody TaskRequestBody taskBody)
    {
        TaskEnt task = tasksService.modifyTask(id, taskBody);
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @ApiOperation("Пометить задачу как сделанную")
    @PutMapping("tasks/mark-done/{id}")
    public ResponseEntity markAsDoneTask(
            @PathVariable("id") UUID id)
    {
        tasksService.markAsDoneTask(id);
        return new ResponseEntity(new MyResponse("Задача успешно выполнена", HttpStatus.OK), HttpStatus.OK);
    }

    @ApiOperation("Удалить задачу из списка")
    @DeleteMapping("tasks/{id}")
    public ResponseEntity removeTask(
            @PathVariable("id") UUID id)
    {
        tasksService.removeTask(id);
        return new ResponseEntity(new MyResponse("Задача успешно удалена", HttpStatus.OK), HttpStatus.OK);
    }

}
