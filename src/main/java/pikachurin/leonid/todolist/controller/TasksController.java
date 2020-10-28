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

import java.util.*;

@Api(description = "Операции с задачами")
@RestController
public class TasksController {
    @Autowired
    private ListRepo listRepo;
    @Autowired
    private TaskRepo taskRepo;

    @ApiOperation("Получить задачи из списка")
    @GetMapping("lists/{list_id}/tasks")
    public ResponseEntity getTasks(
            @PathVariable("list_id") UUID listId,
            @RequestParam(required = false, defaultValue = "10") Integer quantity,
            @RequestParam(required = false, defaultValue = "0") Integer page)
    {
        Optional<ListEnt> optionalList = listRepo.findById(listId);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(listId);
        if (quantity > 100 || quantity < 1) quantity = 10;

        Pageable pageable = PageRequest.of(page, quantity);
        return new ResponseEntity(taskRepo.findAllByList_Id(listId, pageable).getContent(), HttpStatus.OK);
    }

    @ApiOperation("Получить задачу по её id")
    @GetMapping("tasks/{id}")
    public ResponseEntity getTask(
            @PathVariable("id") UUID id)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
    }

    @ApiOperation("Создать новую задачу в списке")
    @PostMapping("lists/{list_id}/tasks")
    public ResponseEntity addTask(
            @PathVariable("list_id") UUID listId,
            @RequestBody TaskRequestBody taskBody)
    {
        Optional<ListEnt> optionalList = listRepo.findById(listId);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(listId);
        if (taskBody.getName().isEmpty())
            throw new IncorrectNameException();

        TaskEnt task = new TaskEnt();
        task.setName(taskBody.getName());
        task.setDescription(taskBody.getDescription());
        task.setPriority(taskBody.getPriority());
        task.setIsDone(taskBody.getIsDone());
        task.setList(optionalList.get());
        task = taskRepo.saveAndFlush(task);

        return new ResponseEntity(task, HttpStatus.CREATED);
    }

    @ApiOperation("Изменить задачу")
    @PutMapping("tasks/{id}")
    public ResponseEntity modifyTask(
            @PathVariable("id") UUID id,
            @RequestBody TaskRequestBody taskBody)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);
        if (taskBody.getName().isEmpty())
            throw new IncorrectNameException();

        TaskEnt task = optionalTask.get();
        task.setName(taskBody.getName());
        task.setDescription(taskBody.getDescription());
        task.setPriority(taskBody.getPriority());
        task.setIsDone(taskBody.getIsDone());
        taskRepo.flush();
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @ApiOperation("Пометить задачу как сделанную")
    @PutMapping("tasks/mark-done/{id}")
    public ResponseEntity markAsDoneTask(
            @PathVariable("id") UUID id)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        TaskEnt task = optionalTask.get();
        task.setIsDone(true);
        taskRepo.flush();
        return new ResponseEntity(new MyResponse("Задача успешно выполнена", HttpStatus.OK), HttpStatus.OK);
    }

    @ApiOperation("Удалить задачу из списка")
    @DeleteMapping("tasks/{id}")
    public ResponseEntity removeTask(
            @PathVariable("id") UUID id)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        TaskEnt task = optionalTask.get();
        taskRepo.delete(task);
        return new ResponseEntity(new MyResponse("Задача успешно удалена", HttpStatus.OK), HttpStatus.OK);
    }

}
