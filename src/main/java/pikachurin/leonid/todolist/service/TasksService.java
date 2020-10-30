package pikachurin.leonid.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import pikachurin.leonid.todolist.entity.*;
import pikachurin.leonid.todolist.exception.*;
import pikachurin.leonid.todolist.model.TaskRequestBody;
import pikachurin.leonid.todolist.repository.*;

import java.util.*;

@Service
public class TasksService {
    @Autowired
    ListRepo listRepo;
    @Autowired
    TaskRepo taskRepo;

    public List<TaskEnt> getTasks(UUID listId, Integer quantity, Integer page)
    {
        Optional<ListEnt> optionalList = listRepo.findById(listId);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(listId);
        if (quantity > 100 || quantity < 1) quantity = 10;

        Pageable pageable = PageRequest.of(page, quantity);
        return taskRepo.findAllByList_Id(listId, pageable).getContent();
    }

    public TaskEnt getTask(UUID id)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        return optionalTask.get();
    }

    public TaskEnt addTask(UUID listId, TaskRequestBody taskBody)
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
        return task;
    }

    public TaskEnt modifyTask(UUID id, TaskRequestBody taskBody)
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
        return task;
    }

    public void markAsDoneTask(UUID id)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        TaskEnt task = optionalTask.get();
        task.setIsDone(true);
        taskRepo.flush();
    }

    public void removeTask(UUID id)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        TaskEnt task = optionalTask.get();
        taskRepo.delete(task);
    }
}
