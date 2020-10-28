package pikachurin.leonid.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pikachurin.leonid.todolist.entity.*;
import pikachurin.leonid.todolist.exception.*;
import pikachurin.leonid.todolist.repository.*;

import java.util.*;

@RestController
public class TasksController {
    @Autowired
    private ListRepo listRepo;
    @Autowired
    private TaskRepo taskRepo;

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

    @GetMapping("tasks/{id}")
    public ResponseEntity getTask(
            @PathVariable("id") UUID id)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
    }

    @PostMapping("lists/{list_id}/tasks")
    public ResponseEntity addTask(
            @PathVariable("list_id") UUID listId,
            @RequestParam String name,
            @RequestParam Optional<String> description,
            @RequestParam Optional<Integer> priority)
    {
        Optional<ListEnt> optionalList = listRepo.findById(listId);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(listId);
        if (name.isEmpty())
            throw new IncorrectNameException();

        TaskEnt task = new TaskEnt();
        task.setName(name);
        description.ifPresent(task::setDescription);
        priority.ifPresent(task::setPriority);
        task.setList(optionalList.get());
        task = taskRepo.saveAndFlush(task);

        return new ResponseEntity(task, HttpStatus.CREATED);
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity modifyTask(
            @PathVariable("id") UUID id,
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> description,
            @RequestParam Optional<Integer> priority,
            @RequestParam Optional<Boolean> isDone)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        TaskEnt task = optionalTask.get();
        name.ifPresent(task::setName);
        description.ifPresent(task::setDescription);
        priority.ifPresent(task::setPriority);
        isDone.ifPresent(task::setIsDone);
        taskRepo.flush();
        return new ResponseEntity(task, HttpStatus.OK);
    }

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
