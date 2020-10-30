package pikachurin.leonid.todolist.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pikachurin.leonid.todolist.entity.*;
import pikachurin.leonid.todolist.exception.*;
import pikachurin.leonid.todolist.model.*;
import pikachurin.leonid.todolist.service.ListsService;

import java.sql.Timestamp;
import java.util.*;

/**
 * Контроллер запросов по операциям со Списками задач
 */
@Api(description = "Операции со списками задач")
@RestController
@RequestMapping("lists")
public class ListsController {
    @Autowired
    private ListsService listsService;

    /**
     * Получить списки с сортировкой и фильтрацией
     * @param quantity - количество списков на странице
     * @param page - номер страницы
     * @param sort - параметр сортировки
     * @param isInvert - инвертировать порядок списков
     * @param name - значение фильтрации для name
     * @param createDate - значение фильтрации для createDate
     * @param modifyDate - значение фильтрации для modifyDate
     * @return массив списков
     */
    @ApiOperation("Получить списки с сортировкой и фильтрацией")
    @GetMapping
    public ResponseEntity<List<ListEnt>> getLists(
            @RequestParam(required = false, defaultValue = "10") Integer quantity,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "createDate") String sort,
            @RequestParam(required = false, defaultValue = "false") Boolean isInvert,
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Timestamp> createDate,
            @RequestParam(required = false) Optional<Timestamp> modifyDate)
    {
        List<ListEnt> lists = listsService.getLists(quantity, page, sort, isInvert);
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    /**
     * Получить список по его id
     * @param id - id списка
     * @return список
     */
    @ApiOperation("Получить список по его id")
    @GetMapping("/{id}")
    public ResponseEntity<ListEnt> getList(
            @PathVariable("id") UUID id)
    {
        ListEnt list = listsService.getList(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Создать новый список
     * @param listBody - тело запроса
     * @return созданный список
     */
    @ApiOperation("Создать новый список")
    @PostMapping
    public ResponseEntity<ListEnt> createList(
            @RequestBody ListRequestBody listBody)
    {
        ListEnt list = listsService.createList(listBody);
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    /**
     * Изменить список
     * @param id - id списка
     * @param listBody - тело запроса
     * @return изменённый список
     */
    @ApiOperation("Изменить список")
    @PutMapping("/{id}")
    public ResponseEntity<ListEnt> modifyList(
            @PathVariable("id") UUID id,
            @RequestBody ListRequestBody listBody)
    {
        ListEnt list = listsService.modifyList(id, listBody);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Удалить список
     * @param id - id списка
     */
    @ApiOperation("Удалить список")
    @DeleteMapping("/{id}")
    public ResponseEntity<MyResponse> removeList(
            @PathVariable("id") UUID id)
    {
        listsService.removeList(id);
        return new ResponseEntity<>(new MyResponse("Список успешно удален", HttpStatus.OK), HttpStatus.OK);
    }
}

