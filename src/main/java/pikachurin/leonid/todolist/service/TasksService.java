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

/**
 * Сервис по операциям с Задачами
 */
@Service
public class TasksService {
    @Autowired
    private ListRepo listRepo;
    @Autowired
    private TaskRepo taskRepo;

    /**
     * Получить задачи из списка
     * @param listId - id списка
     * @param quantity - количество задач на странице
     * @param page - номер страницы
     * @return массив задач
     */
    public List<TaskEnt> getTasks(UUID listId, int quantity, int page)
    {
        Optional<ListEnt> optionalList = listRepo.findById(listId);
        if (optionalList.isEmpty())
            throw new ListNotFoundException(listId);

        if (quantity > 100 || quantity < 1) quantity = 10;
        Pageable pageable = PageRequest.of(page, quantity);

        return taskRepo.findAllByList_Id(listId, pageable).getContent();
    }

    /**
     * Получить задачу по её id
     * @param id - id задачи
     * @return задача
     */
    public TaskEnt getTask(UUID id)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        return optionalTask.get();
    }

    /**
     * Создать новую задачу в списке
     * @param listId - id списка
     * @param taskBody - параметры новой задачи
     * @return созданная задача
     */
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

    /**
     * Изменить задачу
     * @param id - id задачи
     * @param taskBody - параметры изменённой задачи
     * @return изменённая задача
     */
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

    /**
     * Пометить задачу как сделанную
     * @param id - id задачи
     */
    public void markAsDoneTask(UUID id)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        TaskEnt task = optionalTask.get();
        task.setIsDone(true);
        taskRepo.flush();
    }

    /**
     * Удалить задачу из списка
     * @param id - id задачи
     */
    public void removeTask(UUID id)
    {
        Optional<TaskEnt> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(id);

        TaskEnt task = optionalTask.get();
        taskRepo.delete(task);
    }
}
